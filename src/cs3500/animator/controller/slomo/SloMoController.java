package cs3500.animator.controller.slomo;

import cs3500.animator.controller.ISimpleAnimationController;
import cs3500.animator.controller.InteractiveFbyFController;
import cs3500.animator.controller.ViewListener;
import cs3500.animator.view.visual.InteractiveAnimationNotifier;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Allows a user to parse a SloMo file into the controller, allowing for fixed periods of
 * frame rate change.
 */
public class SloMoController extends InteractiveFbyFController
    implements ISimpleAnimationController, ISloMoController, ViewListener {

  private int frameCounter;
  protected final Map<Integer, Integer> sloMoStart;
  protected final Map<Integer, Integer> resetTo;
  protected final Map<Integer, Integer> sloMoEndTrack;

  private int customSetFrameRate;

  /**
   * Creates a model based off of the read location, then initializes the view for animation.
   * @param view      The view to display to.
   * @param frameRate The framerate at which the view should run.
   * @param rd        Where text should be input from.
   */
  public SloMoController(InteractiveAnimationNotifier view,
      int frameRate, Readable rd) {
    super(view, frameRate, rd);
    this.frameCounter = 0;

    this.sloMoStart = new HashMap<>();
    this.sloMoEndTrack = new HashMap<>();
    this.resetTo = new HashMap<>();
    this.customSetFrameRate = frameRate;
  }

  @Override
  public void addSloMoFile(String fileName) throws IllegalArgumentException {
    try {
      BufferedReader br = getReader(fileName);

      List<Integer> usedFrames = new ArrayList<>();

      for (String line = br.readLine(); line != null; line = br.readLine()) {
        String[] splitLine = line.split(" ");

        if (splitLine.length != 3) {
          throw new IllegalArgumentException("SloMoFile is malformed, line should be:\n"
              + "<Speed> <Start Frame> <End Frame>");
        }

        int speed = Integer.parseInt(splitLine[0]);
        int startFrame = Integer.parseInt(splitLine[1]);
        int endFrame = Integer.parseInt(splitLine[2]);

        System.out.println(speed + "," + startFrame + "," + endFrame);


        if (startFrame > endFrame) {
          throw new IllegalArgumentException("Start frame cannot be after end frame.");
        } else if (startFrame > model.getMaximumFrame() || endFrame > model.getMaximumFrame()) {
          throw new IllegalArgumentException("SloMo occurs outside of animation range.");
        } else if (speed < 1 || startFrame < 1 || endFrame < 1) {
          throw new IllegalArgumentException("None of the SloMo values can be below 1.");
        } else if (startFrame == endFrame) {
          continue;
        }

        for (int i = startFrame; i < endFrame; i++) {
          if (usedFrames.contains(i)) {
            throw new IllegalArgumentException("SloMos cannot overlap.");
          } else {
            usedFrames.add(i);
          }
        }

        Integer startTime = this.sloMoStart.put(startFrame, speed);
        this.sloMoEndTrack.put(endFrame, startFrame);

        if (startTime != null) {
          throw new IllegalArgumentException("Multiple SloMos cannot start on the same frame.");
        }

      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(
          "SloMo File Missing. (Need file named \"" + fileName + "\")");
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to read from file.");
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("SloMo file is Malformed.");
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Not all fields in SloMo file are numbers.");
    }
  }

  @Override
  public void restart() {
    this.frameCounter = 0;
    this.frameRate = this.customSetFrameRate;
    super.restart();
  }

  @Override
  public void setAnimationSpeed(int frameRate) {
    super.setAnimationSpeed(frameRate);
    this.customSetFrameRate = frameRate;
  }

  @Override
  protected void advanceModel() {
    this.frameCounter ++;

    if (this.sloMoEndTrack.containsKey(this.frameCounter)) {
      int startFrame = this.sloMoEndTrack.get(this.frameCounter);
      this.frameRate = this.resetTo.get(startFrame);
      this.timer.setDelay(this.calculateFrameDelay());
    }

    if (this.sloMoStart.containsKey(this.frameCounter)) {
      int newFramerate = this.sloMoStart.get(this.frameCounter);
      this.resetTo.put(this.frameCounter, this.frameRate);
      this.frameRate = newFramerate;
      this.timer.setDelay(this.calculateFrameDelay());
    }

    super.advanceModel();
  }

  /**
   * Sets up a BufferedReader that parses in the necessary information to set up SloMo.
   * @param input The textual input needed to set up the reader.
   * @return The reader containing the SloMo information.
   */
  protected BufferedReader getReader(String input) {
    try {
      return new BufferedReader(
          new InputStreamReader(
              new DataInputStream(
                  new FileInputStream(input))));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(
          "SloMo File Missing. (Need file named \"" + input + "\")");
    }
  }
}
