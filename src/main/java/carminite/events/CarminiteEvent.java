package carminite.events;

public abstract class CarminiteEvent<T extends CarminiteEvent<T>> {
	boolean isCanceled = false;

	public abstract T post();

	protected CarminiteEvent() {}
}