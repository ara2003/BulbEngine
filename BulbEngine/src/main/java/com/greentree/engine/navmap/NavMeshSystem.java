package com.greentree.engine.navmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.joml.Vector2f;

import com.greentree.common.graph.Graph;
import com.greentree.common.math.Mathf;
import com.greentree.common.time.Time;
import com.greentree.engine.Cameras;
import com.greentree.engine.component.Transform;
import com.greentree.engine.core.object.GameSystem;
import com.greentree.graphics.Color;
import com.greentree.graphics.Graphics;

public class NavMeshSystem extends GameSystem {

	private NavMesh mesh;
	private float radius;

	public void bake(){
		mesh = new NavMesh(getAllComponents(NavMeshObstacle.class), -1000, -1000, 2000, 2000);
	}

	private boolean canGo(Vector2f from, Vector2f to) {
		for(var a : getAllComponents(NavMeshObstacle.class))if(!a.canGo(from, to, radius))return false;
		return true;
	}

	@Override
	public void update() {
		Cameras.getMainCamera().translateAsWorld();
		for(NavMeshAgent agent : getAllComponents(NavMeshAgent.class)) {
			radius = agent.getRadius();
			LinkedList<Vector2f> points = (LinkedList<Vector2f>) agent.getPath();
			if(points.isEmpty())continue;
			Transform t = agent.getComponent(Transform.class);
			var point = points.getLast();
			if(!canGo(t.xy(), point)) {
				if(mesh == null)bake();
				var res = mesh.get(t.xy(), point);
				System.out.println(points);
				points.removeLast();
				points.addAll(res);
				System.out.println(points);
				continue;
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
			t.addXY(dx, dy);
			{//graphics
				Color.white.bind();
				float x0 = t.x(), y0 = t.y();
				for(int i = points.size()-1; i>=0; i--) {
					float x = points.get(i).x(), y = points.get(i).y();
					Graphics.drawLine(x, y, x0, y0);
					x0 = x;
					y0 = y;
				}
			}
		}
		if(mesh == null)bake();
		mesh.draw();
		Cameras.getMainCamera().untranslate();
	}
	
	public class NavMesh {

		private boolean checkJoint(Vector2f a, Vector2f b){
//			var dis = a.distanceSquared(b);
//			if(dis > 250000)return false;
//			if(dis < 300)return false;
			return NavMeshSystem.this.canGo(a, b);
		}
		
		private Graph<Vector2f> g = new Graph<>();

		public NavMesh(List<NavMeshObstacle> list, float x, float y, float w, float h) {
			List<Vector2f> list0 = new ArrayList<>();
			final float p = 200f;
			for(float x0 = x; x0 < x+w; x0 += p)
				for(float y0 = y; y0 < y+w; y0 += p) {
					Vector2f vec = new Vector2f(x0, y0);
					if(list.parallelStream().filter(e -> !e.isInside(vec, 100)).findAny().isPresent())
						list0.add(vec);
				}
			g.addAll(list0);
			for(Vector2f a : list0)
				for(Vector2f b : list0)if(a == b)break;
					else if(checkJoint(a, b)) g.addJoint(a, b);
		}

		void draw() {
			Color.red.bind();
			for(var a : getAllComponents(NavMeshObstacle.class)) {
				Graphics.drawRect(a.getX(), a.getY(), a.getWidth(), a.getHeight());
				Graphics.drawCircle(a.getX(), a.getY(), 100);
				Graphics.drawCircle(a.getX(), a.getY()+a.getHeight(), 100);
				Graphics.drawCircle(a.getX()+a.getWidth(), a.getY(), 100);
				Graphics.drawCircle(a.getX()+a.getWidth(), a.getY()+a.getHeight(), 100);
			}
			Color.blue.bind();
			for(Vector2f a : g.getVertex()) {
				Graphics.drawOval(a.x, a.y, 10, 10);
				for(Vector2f b : g.getJoints(a)) {
					Graphics.drawLine(a.x, a.y, b.x, b.y);
				}
			}
		}

		public Collection<? extends Vector2f> get(Vector2f from, Vector2f to) {
			g.add(from);
			for(Vector2f v : g.getVertex()) if(checkJoint(from, v))g.addJoint(from, v);

			g.add(to);
			for(Vector2f v : g.getVertex()) if(checkJoint(v, to)) g.addJoint(to, v);

			System.out.println(from + " " + to);
		 	System.out.println(g.getJoints(from).size());
		 	System.out.println(g.getJoints(to).size());
			
			var res = g.getPathFinder(j -> (double)j.getA().distanceSquared(j.getB())).get(from, to);

			res.remove(0);
			System.out.println(res);
			
			g.remove(to);
			g.remove(from);
			return res;
		}

	}

}
