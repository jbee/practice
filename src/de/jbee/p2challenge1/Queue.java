package de.jbee.p2challenge1;

public final class Queue<T>
		implements IQueue<T> {

	static interface IElement<T> {

		IElement<T> add( T e );

		boolean isEmpty();

		IElement<T> remove();

		T top();

		int size();
	}

	static final class EmptyElement<T>
			implements IElement<T> {

		@Override
		public IElement<T> add( T e ) {
			return new Element<T>( e, this );
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public IElement<T> remove() {
			throw new AssertionError( "remove can't be performed on an empty queue" );
		}

		@Override
		public T top() {
			throw new AssertionError( "top can't be performed on an empty queue" );
		}

		@Override
		public int size() {
			return 0;
		}

	}

	static final class Element<T>
			implements IElement<T> {

		private final T value;
		private final IElement<T> next;

		Element( T value, IElement<T> next ) {
			this.value = value;
			this.next = next;
		}

		@Override
		public IElement<T> add( T e ) {
			return new Element<T>( value, this );
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public IElement<T> remove() {
			return next;
		}

		@Override
		public T top() {
			return value;
		}

		@Override
		public int size() {
			return 1 + next.size();
		}

	}

	static final IElement<?> EMPTY = new EmptyElement<Object>();

	@SuppressWarnings ( "unchecked" )
	private IElement<T> root = (IElement<T>) EMPTY;

	@Override
	public void add( T item ) {
		root = root.add( item );
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public void remove() {
		root = root.remove();
	}

	@Override
	public int size() {
		return root.size();
	}

	@Override
	public T top() {
		return root.top();
	}

}
