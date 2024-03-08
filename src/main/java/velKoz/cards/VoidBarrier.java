package velKoz.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import velKoz.VelKozMod;
import velKoz.powers.CommonPower;
import velKoz.powers.OrganicDeconstructionPower;

import static velKoz.VelKozMod.makeSkillCardPath;

public class VoidBarrier extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = VelKozMod.makeID(VoidBarrier.class.getSimpleName());
    public static final String IMG = makeSkillCardPath("Skill.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = velKoz.characters.VelKoz.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    private static final int OD_STACKS = 3;
    private static final int UPGRADE_PLUS_OD_STACKS=1;

    // /STAT DECLARATION/


    public VoidBarrier() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = OD_STACKS;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(p, p, new OrganicDeconstructionPower(p, p, magicNumber,1,3), magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_OD_STACKS);
            initializeDescription();
        }
    }
}
