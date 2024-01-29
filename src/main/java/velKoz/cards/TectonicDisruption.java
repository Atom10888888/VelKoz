package velKoz.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import velKoz.VelKozMod;
import velKoz.powers.DeconstructionPower;
import velKoz.powers.OrganicDeconstructionPower;
import velKoz.util.PowerHandler;

import static velKoz.VelKozMod.makeSkillCardPath;

public class TectonicDisruption extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = VelKozMod.makeID(TectonicDisruption.class.getSimpleName());
    public static final String IMG = makeSkillCardPath("Skill.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = velKoz.characters.VelKoz.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DESTRUCTION_STACKS = 3;
    private static final int UPGRADE_PLUS_DESTRUCTION_STACKS = 1;
    private static final int WEAK = 1;
    private static final int UPGRADE_PLUS_WEAK = 1;



    // /STAT DECLARATION/


    public TectonicDisruption() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = DESTRUCTION_STACKS;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = WEAK;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower organicDeconstructionPower = PowerHandler.findPower(p, OrganicDeconstructionPower.class);
        AbstractPower deconstructionPower = PowerHandler.findPower(m, DeconstructionPower.class);
        if (deconstructionPower != null && organicDeconstructionPower != null){
            int stacksLimit = ((OrganicDeconstructionPower)organicDeconstructionPower).getStacksLimit();
            int explodeDamage = ((OrganicDeconstructionPower)organicDeconstructionPower).getExplodeDamage();
            for (int i = 0; i < magicNumber; i++) {
                ((DeconstructionPower)deconstructionPower).addStack(stacksLimit,explodeDamage,p);
            }
        }
        addToBot(new ApplyPowerAction(m, p, new WeakPower(p, this.defaultSecondMagicNumber,false), this.defaultSecondMagicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_DESTRUCTION_STACKS);
            this.upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_WEAK);
            this.initializeDescription();
        }
    }
}
