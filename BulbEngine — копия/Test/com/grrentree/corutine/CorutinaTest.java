package com.grrentree.corutine;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.greentree.engine.Time;
import com.greentree.engine.Timer;
import com.greentree.engine.corutine.Corutine;
import com.greentree.engine.corutine.WaitForSeconds;

public class CorutinaTest {
	
	Timer timer = new Timer();

	@Test(expected = NullPointerException.class)
	public void testNullConstructor() {
		new Corutine(null, null);
	}
	
	@Ignore
	@Test
	public void testWaitForSeconds() {
		WaitForSeconds wait = new WaitForSeconds(1f);
		
		
		float a = 0;
		
		timer.start(1);
		while(wait.keepWaiting());
		a += Math.abs(timer.finish(1)*1000f / Time.TimePerSecond - 1000);

		timer.start(1);
		while(wait.keepWaiting());
		a += Math.abs(timer.finish(1)*1000f / Time.TimePerSecond - 1000);

		timer.start(1);
		while(wait.keepWaiting());
		a += Math.abs(timer.finish(1)*1000f / Time.TimePerSecond - 1000);

		timer.start(1);
		while(wait.keepWaiting());
		a += Math.abs(timer.finish(1)*1000f / Time.TimePerSecond - 1000);
		
		System.out.println(a);
		
	}

	@Test
	public void testStart() {
		final Corutine corutine = new Corutine(()-> {
		}, null);

		for(int i = 0; i < 10; i++) {
			corutine.addCorutine(new Corutine(()-> {
//				System.out.println("s");
			}, new WaitForSeconds(.1f)));
		}

		float a, b;
		
		timer.start(0);
		while(!corutine.run());
		System.out.println(a = timer.finish(0)*1f / Time.TimePerSecond);
		timer.start(0);
		while(!corutine.run());
		System.out.println(b = timer.finish(0)*1f / Time.TimePerSecond);
		
		System.out.println(Math.abs(a - b));
	}
	
	@Test
	public void testUnLoop() {
		class A {
			String log = "";
		}
		final A a = new A();
		final Corutine corutine = new Corutine(()-> {
			a.log += "1";
		}, null).addCorutine(new Corutine(()-> {
			a.log += "2";
		}, null)).addCorutine(new Corutine(()-> {
			a.log += "3";
		}, null));
		while(true) if(corutine.run()) break;
		assertEquals("123", a.log);
	}
}
