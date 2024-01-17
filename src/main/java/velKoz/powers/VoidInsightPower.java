package velKoz.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import velKoz.VelKozMod;
import velKoz.util.TextureLoader;

import static velKoz.VelKozMod.makePowerPath;

public class VoidInsightPower extends AbstractPower implements CloneablePowerInterface {

    public AbstractCreature source;

    public static final String POWER_ID = VelKozMod.makeID("VoidInsightPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public int stacksToAdd;
    public int stacksLimit;


    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public VoidInsightPower(final AbstractCreature owner, final AbstractCreature source, final int amount, final int stacksToAdd, final int stacksLimit) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.stacksToAdd = stacksToAdd;
        this.stacksLimit = stacksLimit;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (stacksToAdd == 1) {
            description = DESCRIPTIONS[0] + stacksToAdd + DESCRIPTIONS[1] + DESCRIPTIONS[3] + stacksLimit + DESCRIPTIONS[4] + amount + DESCRIPTIONS[5];
        } else if (stacksToAdd > 1){
            description = DESCRIPTIONS[0] + stacksToAdd + DESCRIPTIONS[2] + DESCRIPTIONS[3] + stacksLimit + DESCRIPTIONS[4] + amount + DESCRIPTIONS[5];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new VoidInsightPower(owner, source, stacksToAdd, stacksLimit, amount);
    }
}
