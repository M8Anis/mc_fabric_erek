package github.d3d9_dll.minecraft.fabric.erek;

public class Version {

    public static final int IS_DEBUG = 1;
    public static final int VERSION_RELEASE_TYPE = 0x1A_A19A;// 0x1A_A19A (Alpha), 0x2B_BE7A (Beta), 0x3C_91E1 (Release)
    public static final int VERSION_NUMBER = 8;
    @SuppressWarnings({"PointlessArithmeticExpression", "RedundantSuppression"})
    public static final long VERSION_SERIAL = ((long) VERSION_RELEASE_TYPE << 32) + (IS_DEBUG << 30) + VERSION_NUMBER;

}
