package com.grrentree.corutine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import com.greentree.engine.Time;
import com.greentree.engine.Timer;
import com.greentree.engine.corutine.Corutine;
import com.greentree.engine.corutine.WaitForSeconds;

public class CorutinaTest {
	
	Timer timer = new Timer();
	
	@Test
	public void testNullConstructor() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			new Corutine(null, null);
		});
		
	}
	
	@Timeout(2001)
	@Test
	public void testStart() {
		final Corutine corutine = new Corutine(()-> {
		}, null);
		
		for(int i = 0; i < 10; i++) {
			corutine.addCorutine(new Corutine(()-> {
				
			}, new WaitForSeconds(.1f)));
		}
		
		float a, b;
		
		timer.start(0);
		while(!corutine.run());
		a = (timer.finish(0)*1000f) / Time.TimePerSecond;
		timer.start(0);
		while(!corutine.run());
		b = (timer.finish(0)*1000f) / Time.TimePerSecond;
		
		assertTrue(Math.abs(a - b) < 1);
		
	}
	
	@Test
	public void testUnLoop() {
		class Log {
			String log = "";
		}
		final Log a = new Log();
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
	
	@Timeout(1001)
	@Test
	public void testWaitForSeconds() {
		WaitForSeconds wait = new WaitForSeconds(1f);
		
		while(wait.keepWaiting());
	}
}
