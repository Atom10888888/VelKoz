package velKoz.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import velKoz.VelKozMod;
import velKoz.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static velKoz.VelKozMod.makePowerPath;

public class DeconstructionPower extends AbstractPower implements CloneablePowerInterface {
    public static final Logger logger = LogManager.getLogger(VelKozMod.class.getName());
    public AbstractCreature source;

    public static final String POWER_ID = VelKozMod.makeID("DeconstructionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("deconstruction_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("deconstruction_power32.png"));

    public DeconstructionPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        AbstractPlayer p = AbstractDungeon.player;
        if (info.owner != null && info.owner.isPlayer && info.type == DamageInfo.DamageType.NORMAL) {
            for (AbstractPower power : p.powers){
                if (power instanceof OrganicDeconstructionPower){
                    OrganicDeconstructionPower organicDeconstructionPower = (OrganicDeconstructionPower)power;
                    int stacksToAdd = organicDeconstructionPower.getStacksToAdd();
                    int stacksLimit = organicDeconstructionPower.getStacksLimit();
                    int explodeDamage = organicDeconstructionPower.amount;
                    for (int i = 0; i < stacksToAdd; i++) {
                        addStack(stacksLimit,explodeDamage,p);
                    }
                }
            }
        }
        return damageAmount;
    }

    // 这个方法用于增加 Deconstruction 层数
    public void addStack(int stacksLimit, int explodeDamage, AbstractPlayer p) {
        this.amount += 1;
        if(this.amount % stacksLimit == 0){
            this.amount -= stacksLimit;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(this.owner, new DamageInfo(p, explodeDamage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE)
            );
        }
    }

    public int getAmount(){
        return this.amount;
    }


    @Override
    public AbstractPower makeCopy() {
        return new DeconstructionPower(owner, source, amount);
    }
}
