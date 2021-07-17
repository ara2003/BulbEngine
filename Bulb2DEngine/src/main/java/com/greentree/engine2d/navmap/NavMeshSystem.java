package com.greentree.engine2d.navmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.greentree.common.graph.Graph;
import com.greentree.common.math.Mathf;
import com.greentree.common.math.vector.AbstractVector2f;
import com.greentree.common.math.vector.Vector2f;
import com.greentree.common.math.vector.VectorAction3f;
import com.greentree.common.time.Time;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.system.GameSystem.MultiBehaviour;

public class NavMeshSystem extends MultiBehaviour {

	private NavMesh mesh;
	private float radius = 100;
	//	{
	//		bake();
	//	}
	public void bake(){
		mesh = new NavMesh(getAllComponents(NavMeshObstacle.class), -2000, -2000, 4000, 4000);
	}

	private boolean canGo(AbstractVector2f from, AbstractVector2f to) {
		return getAllComponents(NavMeshObstacle.class).parallelStream().filter(e -> e.canGo(from, to, radius)).findAny().isPresent();
	}
	private boolean isInside(AbstractVector2f vec) {
		return getAllComponents(NavMeshObstacle.class).parallelStream().filter(e -> !e.isInside(vec, 100)).findAny().isPresent();
	}

	@Override
	public void update() {
		for(NavMeshAgent agent : getAllComponents(NavMeshAgent.class)) {
			radius = agent.getRadius();
			LinkedList<AbstractVector2f> points = (LinkedList<AbstractVector2f>) agent.getPath();
			if(points.isEmpty())continue;
			VectorAction3f t = agent.getComponent(Transform.class).position;
			var point = points.getLast();
			if(!canGo(t.xy(), point)) {
				if(mesh == null)bake();
				var res = mesh.get(t.xy(), point);
				points.removeLast();
				points.addAll(res);
			}
			float dx = point.x() - t.x();
			float dy = point.y() - t.y();
			float len = Mathf.sqrt(dx*dx+dy*dy);
			if(len < 20) {
				points.removeLast();
				continue;
			}
			dx = dx * Time.getDelta() * agent.getSpeed() / len;
			dy = dy * Time.getDelta() * agent.getSpeed() / len;
			t.add(dx, dy, 0);
		}
	}

	public class NavMesh {

		private Graph<AbstractVector2f> g = new Graph<>();

		public NavMesh(List<NavMeshObstacle> list, float x, float y, float w, float h) {
			List<AbstractVector2f> list0 = new ArrayList<>();
			final float p = 100f;
			for(float x0 = x; x0 < x+w; x0 += p) for(float y0 = y; y0 < y+w; y0 += p) {
				Vector2f vec = new Vector2f(x0, y0);
				if(isInside(vec))
					list0.add(vec);
			}
			g.addAll(list0);
			for(AbstractVector2f a : list0)
				for(AbstractVector2f b : list0)if(a == b)break;
				else if(checkJoint(a, b)) g.addJoint(a, b);
		}

		private boolean checkJoint(AbstractVector2f a, AbstractVector2f b){
			var dis = a.distanceSquared(b);
			if(dis > 90000 || dis < 300)return false;
			return canGo(a, b);
		}

		public Collection<? extends AbstractVector2f> get(AbstractVector2f from, AbstractVector2f to) {
			boolean flag_from = !g.contains(from), flag_to = !g.contains(to);

			if(flag_from)for(AbstractVector2f v : new ArrayList<>(g.getVertex())) if(checkJoint(from, v))g.addJoint(from, v);
			if(flag_to)for(AbstractVector2f v : new ArrayList<>(g.getVertex())) if(checkJoint(v, to)) g.addJoint(to, v);

			var res = g.getPathAStar(from, to, j -> (double)j.first.distanceSquared(j.seconde));

			if(flag_from)g.remove(to);
			if(flag_to)g.remove(from);

			res.remove(res.size()-1);
			return res;
		}

	}

}
