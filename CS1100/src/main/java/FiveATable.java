

public class FiveATable {
  // Private Variables
  // Private Variables
  private double OTEMP;
  private double OG;
  private double RHO;
  private double HYC;
  private double RHOT;
  private double VCF;
  private double ALPHA;
  private double ALPHASQD;
  private double RHO60;
  private double DELTA;
  private double DELTASQD;
  private double CG;
  private double temp;

  // Constant Variables
  private final double C1 = 141.5f;
  private final double C2 = 999.012f;
  private final double C3 = 131.5f;
  private final double C4 = 0.00001278f;
  private final double C5 = 0.0000000062f;
  private final double K0 = 341.0957f;
  private final double CE = 2.71828182845904f;

  public double CalcFiveATable(double OTEMP, double OG) {
    this.OTEMP = OTEMP;
    this.OG = OG;

    // Calculate the Delta of the Temp to 60 Degrees F
    DELTA = OTEMP - 60.0F;
    DELTASQD = (double) Math.pow(DELTA, 2.0F);

    // Calculate the RHO based on the Observed Gravity
    RHO = C1 * C2 / (C3 + OG);

    // Calculate the HYC
    HYC = 1 - C4 * DELTASQD - C5 * DELTASQD;

    // Calculate the RHOT
    RHOT = HYC * RHO;

    // Calculate the initial Alpha
    ALPHA = K0 / RHOT / RHOT;
    // (double) Math.pow(CE, VCF[i])

    // Calculate the Corrected Gravity
    for (int i = 0; i < 10; i++) {

      // Calculate the Alpha Squared
      ALPHASQD = (double) Math.pow(ALPHA, 2.0f);
      
      // Calculate the VCF
      temp = (ALPHA * -1.0f) * DELTA - 0.8f * ALPHASQD * DELTASQD;
      VCF = (double) Math.pow(CE, temp);

      // Calculate the RHO60
      RHO60 = RHOT / VCF;
      
      // Calculate the ALPHA
      ALPHA = K0 / RHO60 / RHO60;

      // Calculate the final Corrected Gravity @60 Degrees F
      CG = (C1 * C2 / RHO60) - C3;

    }
    // Return the final Corrected Gravit
    return CG;
  }
}
