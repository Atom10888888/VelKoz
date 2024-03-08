package velKoz.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import velKoz.VelKozMod;
import velKoz.util.PowerHandler;
import velKoz.util.TextureLoader;

import static velKoz.VelKozMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class FragmentedLearningPower extends AbstractPower implements CloneablePowerInterface, DeconstructionExplodeListener {
    public AbstractCreature source;

    public static final String POWER_ID = VelKozMod.makeID("FragmentedLearningPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public FragmentedLearningPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        for(AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters){
            AbstractPower deconstructionPower = PowerHandler.findPower(mo, DeconstructionPower.class);
            if (deconstructionPower != null){
                ((DeconstructionPower)deconstructionPower).addDeconstructionExplodeListener(this);
            }
        }

        updateDescription();
    }

    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new FragmentedLearningPower(owner, source, amount);
    }

    @Override
    public void onDeconstructionExplode(AbstractCreature owner) {
        VelKozMod.logger.info("listener active...");
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p,
                new OrganicDeconstructionPower(p, p, amount, 3, 1), amount));
    }
}