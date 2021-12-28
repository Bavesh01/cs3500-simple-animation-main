package cs3500.animator.view.visual;

import cs3500.animator.controller.ViewListener;
import cs3500.animator.view.IView;

/**
 * An interactiveAnimationNotifier notifies all the Viewlisteners it has subscribed of the updates
 * events and changes in the class.
 */
public interface InteractiveAnimationNotifier extends IView {

  /**
   * Adds any listeners to the notification list for this view.
   * @param vl All listeners that should be added to this view.
   */
  void addListeners(ViewListener... vl);

  /**
   * Tells the listeners that a start event has occurred and should be handled.
   */
  void emitStart();

  /**
   * Tells the listeners to handle an event corresponding the pressing of a pause/play button.
   */
  void emitPausePlay();

  /**
   * Tells the listeners to handle an even where the loop button was pressed.
   */
  void emitLoop();

  /**
   * Tells the listeners to handle a stop animation event.
   */
  void stop();

  /**
   * Tells the listeners to handle a start animation event.
   */
  void start();
}
