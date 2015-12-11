package org.roda.rodain.creation;

import java.nio.file.Path;
import java.util.Map;

import org.roda.rodain.core.Main;
import org.roda.rodain.rules.sip.SipPreview;

/**
 * @author Andre Pereira apereira@keep.pt
 * @since 19/11/2015.
 */
public class CreateSips {
  private SipTypes type;
  private Path outputPath;
  private SimpleSipCreator creator;

  private int sipsCount;

  public CreateSips(Path outputPath, SipTypes type) {
    this.type = type;
    this.outputPath = outputPath;
  }

  public void start() {
    Map<SipPreview, String> sips = Main.getSipPreviews();
    sipsCount = sips.size();
    if (type == SipTypes.BAGIT) {
      creator = new BagitSipCreator(outputPath, sips);
      creator.start();
    } else {
      creator = new EarkSipCreator(outputPath, sips);
      creator.start();
    }
  }

  /**
   * @return The total number of SIPs that will be created.
   */
  public int getSipsCount() {
    return sipsCount;
  }

  /**
   * @return The number of SIPs that have already been created.
   */
  public int getCreatedSipsCount() {
    return creator.getCreatedSipsCount();
  }

  /**
   * @return The number of SIPs not created due to an error.
   */
  public int getErrorCount() {
    return creator.getErrorCount();
  }

  /**
   * @return A double resulting of the division of the number of SIPs already
   *         created by the total number of SIPs.
   */
  public double getProgress() {
    return creator.getCreatedSipsCount() / (sipsCount * 1.0);
  }

  /**
   * @return The name of the SIP currently being processed.
   */
  public String getSipName() {
    return creator.getCurrentSipName();
  }

  /**
   * @return The action currently being done on the SIP.
   */
  public String getAction() {
    return creator.getCurrentAction();
  }

  /**
   * Halts the execution of the SIP creator.
   */
  public void cancel() {
    creator.cancel();
  }

}
