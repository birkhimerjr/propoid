package propoid.test.db;

import java.util.Iterator;

import propoid.db.Repository;
import propoid.db.Where;
import propoid.db.cascading.DefaultCascading;
import propoid.db.locator.InMemoryLocator;
import propoid.db.operation.Query;
import propoid.test.Bar;
import propoid.test.Foo;
import propoid.test.FooEx;
import android.app.Application;
import android.app.SearchManager;
import android.database.Cursor;
import android.test.ApplicationTestCase;

/**
 * Test for {@link Query}.
 */
public class QueryTest extends ApplicationTestCase<Application> {

	private Repository repository;

	public QueryTest() {
		super(Application.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		repository = new Repository(new InMemoryLocator());

		((DefaultCascading) repository.cascading).setCascaded(new Foo().barP);

		Foo foo = new Foo();
		foo.barP.set(new Bar());
		repository.insert(foo);
		repository.insert(new FooEx());
	}

	@Override
	protected void tearDown() throws Exception {
		repository.close();

		super.tearDown();
	}

	public void testFoo() {

		Iterator<Foo> foos = repository.query(new Foo()).list().iterator();
		assertTrue(foos.hasNext());
		assertTrue(foos.next().getClass() == Foo.class);
		assertTrue(foos.hasNext());
		assertTrue(foos.next().getClass() == FooEx.class);
		assertFalse(foos.hasNext());
	}

	public void testFooEx() {

		Iterator<FooEx> foos = repository.query(new FooEx()).list().iterator();
		assertTrue(foos.hasNext());
		assertTrue(foos.next().getClass() == FooEx.class);
		assertFalse(foos.hasNext());
	}

	public void testFooHasBar() {
		Foo foo = new Foo();
		Bar bar = new Bar();

		assertEquals(1,
				repository.query(foo, Where.has(foo.barP, bar, Where.any()))
						.count());
	}

	public void testFooHasNotBar() {
		Foo foo = new Foo();
		Bar bar = new Bar();

		assertEquals(1,
				repository.query(foo, Where.hasNot(foo.barP, bar, Where.any()))
						.count());
	}

	public void testFooSetBar() {
		Foo foo = new Foo();

		repository.query(foo).set(foo.barP, null);

		assertEquals(2, repository.query(foo, Where.equal(foo.barP, null))
				.count());
	}

	public void testFooExDelete() {
		FooEx fooEx = new FooEx();

		repository.query(fooEx).delete();

		assertEquals(1, repository.query(new Foo()).count());
	}

	public void testSuggest() {
		Foo foo = new Foo();

		Cursor cursor = repository.query(foo).suggest(foo.stringP, null);

		assertEquals("_id", cursor.getColumnName(0));
		assertEquals(SearchManager.SUGGEST_COLUMN_TEXT_1,
				cursor.getColumnName(1));
		assertEquals(SearchManager.SUGGEST_COLUMN_INTENT_DATA,
				cursor.getColumnName(2));
		assertEquals(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID,
				cursor.getColumnName(3));

		cursor.close();
	}
}