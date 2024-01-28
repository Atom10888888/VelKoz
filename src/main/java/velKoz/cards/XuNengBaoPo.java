package velKoz.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import velKoz.powers.DeconstructionPower;
import velKoz.powers.OrganicDeconstructionPower;
import velKoz.powers.DualDeconstuctionPower;
import velKoz.VelKozMod;
import velKoz.util.PowerHandler;

import static velKoz.VelKozMod.makeAttackCardPath;

public class XuNengBaoPo extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = VelKozMod.makeID(XuNengBaoPo.class.getSimpleName());
    public static final String IMG = makeAttackCardPath("Attack.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = velKoz.characters.VelKoz.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DMG = 4;

    private static final int BLOCK = 14;
    private static final int UPGRADED_PLUS_BLOCK = 4;

    // /STAT DECLARATION/


    public XuNengBaoPo() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower deconstructionPower = PowerHandler.findPower(m, DeconstructionPower.class);
        AbstractPower organicDeconstructionPower = PowerHandler.findPower(p, OrganicDeconstructionPower.class);
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if(deconstructionPower != null && organicDeconstructionPower != null ){
            int deconstructionAmount = ((DeconstructionPower) deconstructionPower).getAmount();
            int stacksToAdd = ((OrganicDeconstructionPower)organicDeconstructionPower).getStacksToAdd();
            int stacksLimit = ((OrganicDeconstructionPower)organicDeconstructionPower).getStacksLimit();
            if (deconstructionAmount + stacksToAdd >= stacksLimit){
                AbstractDungeon.actionManager.addToBottom(
                        new GainBlockAction(p,p,block));
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADED_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
