package velKoz.util;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PowerHandler {
    public static AbstractPower findPower(AbstractCreature m, Class<? extends AbstractPower> powerType) {
        for (AbstractPower power : m.powers) {
            if (powerType.isInstance(power)) {
                return power;
            }
        }
        return null;
    }
}