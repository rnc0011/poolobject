/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

/**
 * @author alumno
 *
 */
public class ReusablePoolTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		ReusablePool pool = ReusablePool.getInstance();
		assertTrue(pool instanceof ReusablePool);

		ReusablePool pool2 = ReusablePool.getInstance();
		assertTrue(pool2 instanceof ReusablePool);

		assertTrue(pool == pool2);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() {

		ReusablePool miPool = ReusablePool.getInstance();

		try {

			Reusable miReusable1 = miPool.acquireReusable();
			assertTrue(miReusable1 instanceof Reusable);
			Reusable miReusable2 = miPool.acquireReusable();
			assertTrue(miReusable2 instanceof Reusable);
			assertFalse(miReusable1.equals(miReusable2));

		} catch (NotFreeInstanceException e) {

			fail("Unexpected NotFreeInstanceException");

		}

		try {

			miPool.acquireReusable();
			fail("NotFreeInstanceException was expected");

		} catch (NotFreeInstanceException e2) {}

	}

	/**
	 * Test method for
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() {
		Reusable reusable = new Reusable();
		ReusablePool pool = ReusablePool.getInstance();
		try {
			pool.releaseReusable(reusable);
			Reusable aux;

			try {
				aux = pool.acquireReusable();
				assertTrue(reusable == aux);
			} catch (NotFreeInstanceException e) {
				fail("Unexpected NotFreeInstanceException");
			}

		} catch (DuplicatedInstanceException e) {
			fail("Unexpected DuplicatedInstanceException");
		}
		
		Reusable reusable2 = new Reusable();
		
		try {
			pool.releaseReusable(reusable2);
			pool.releaseReusable(reusable2);
		}catch (DuplicatedInstanceException e) {}
	
	}

}
