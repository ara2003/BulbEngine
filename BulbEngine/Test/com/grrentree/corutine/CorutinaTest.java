package com.grrentree.corutine;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.greentree.engine.corutine.Corutine;
import com.greentree.engine.corutine.WaitForSeconds;
import com.greentree.engine.corutine.WhileCorutine;

public class CorutinaTest {

	@Test(expected = NullPointerException.class)
	public void testNullConstructor() {
		new Corutine(null, null);
	}
	@Ignore
	@Test
	public void testStart() {
		final Corutine corutine = new WhileCorutine(()-> {
			System.out.println("r");
		}, new WaitForSeconds(1f), new WaitForSeconds(5f)).addCorutine(new WhileCorutine(()-> {
			System.out.println("v");
		}, new WaitForSeconds(1f), new WaitForSeconds(5f)));
		System.out.println("start");
		while(!corutine.run());
		System.out.println("finish");
		//		final var a = new WaitForSeconds(.05f);
		//		boolean b;
		//		for(int i = 0; i < 100; i++) {
		//			b = true;
		//			while(b) {
		//				b = a.keepWaiting();
		//				try {
		//					Thread.sleep(9);
		//				}catch(InterruptedException e) {
		//					// TODO Auto-generated catch block
		//					e.printStackTrace();
		//				}
		//				System.out.println(b);
		//			}
		//		}
	}

	@Test
	public void testUnLoop() {
		class A {
			
			String log = "";
		}
		final A a = new A();
		final Corutine corutine1 = new Corutine(()-> {
			a.log += "1";
		}, null).addCorutine(new Corutine(()-> {
			a.log += "2";
		}, null)).addCorutine(new Corutine(()-> {
			a.log += "3";
		}, null));
		while(true) if(corutine1.run()) break;
		assertEquals("123", a.log);
	}
}
