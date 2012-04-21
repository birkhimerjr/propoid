package propoid.test.util;

import propoid.test.R;
import propoid.util.content.Preference;
import android.preference.PreferenceManager;
import android.test.InstrumentationTestCase;

/**
 * Test for {@link Preference}.
 */
public class PreferenceTest extends InstrumentationTestCase {

	@Override
	protected void tearDown() throws Exception {
		PreferenceManager
				.getDefaultSharedPreferences(getInstrumentation().getContext())
				.edit().clear().commit();
	}

	public void testBoolean() {
		Preference<Boolean> preference = Preference.getBoolean(
				getInstrumentation().getContext(), R.string.preference_test);

		assertFalse(preference.get());

		preference.set(true);

		assertTrue(preference.get());
	}

	public void testInteger() {
		Preference<Integer> preference = Preference.getInteger(
				getInstrumentation().getContext(), R.string.preference_test);

		assertEquals(Integer.valueOf(0), preference.get());

		preference.set(1);

		assertEquals(Integer.valueOf(1), preference.get());
	}

	public void testLong() {
		Preference<Long> preference = Preference.getLong(getInstrumentation()
				.getContext(), R.string.preference_test);

		assertEquals(Long.valueOf(0), preference.get());

		preference.set(1l);

		assertEquals(Long.valueOf(1), preference.get());
	}

	public void testFloat() {
		Preference<Float> preference = Preference.getFloat(getInstrumentation()
				.getContext(), R.string.preference_test);

		assertEquals(Float.valueOf(0), preference.get());

		preference.set(1f);

		assertEquals(Float.valueOf(1), preference.get());
	}

	public void testString() {
		Preference<String> preference = Preference.getString(
				getInstrumentation().getContext(), R.string.preference_test);

		assertEquals(null, preference.get());

		preference.set("test");

		assertEquals("test", preference.get());
	}
}