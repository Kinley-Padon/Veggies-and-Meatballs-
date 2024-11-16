package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The ViewModel for managing state and notifying listeners of state changes.
 * This implementation uses PropertyChangeSupport for handling events.
 *
 * @param <T> The type of state object contained in the ViewModel.
 */
public class ViewModel<T> {

    private final String viewName;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private T state;

    /**
     * Constructor to initialize the ViewModel with a view name.
     *
     * @param viewName The name of the view associated with this ViewModel.
     */
    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    /**
     * Gets the name of the view associated with this ViewModel.
     *
     * @return the view name.
     */
    public String getViewName() {
        return this.viewName;
    }

    /**
     * Gets the current state of the ViewModel.
     *
     * @return the current state.
     */
    public T getState() {
        return this.state;
    }

    /**
     * Updates the state and fires a property change event.
     *
     * @param newState the new state to set.
     */
    public void setState(T newState) {
        T oldState = this.state;
        this.state = newState;
        this.support.firePropertyChange("state", oldState, newState);
    }

    /**
     * Fires a property change event for a specified property name.
     *
     * @param propertyName the name of the property that changed.
     */
    public void firePropertyChanged(String propertyName) {
        this.support.firePropertyChange(propertyName, null, this.state);
    }

    /**
     * Adds a PropertyChangeListener to listen for state changes.
     *
     * @param listener the listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a PropertyChangeListener.
     *
     * @param listener the listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }
}
