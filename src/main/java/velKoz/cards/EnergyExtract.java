package velKoz.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import velKoz.VelKozMod;
import velKoz.powers.DeconstructionPower;
import velKoz.util.PowerHandler;

import static velKoz.VelKozMod.makeAttackCardPath;

public class EnergyExtract extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = VelKozMod.makeID(EnergyExtract.class.getSimpleName());
    public static final String IMG = makeAttackCardPath("Attack.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = velKoz.characters.VelKoz.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;

    private static final int DRAW = 0;
    private static final int UPGRADE_PLUS_DRAW = 1;


    // /STAT DECLARATION/


    public EnergyExtract() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = DRAW;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int deconstructionAmount = 0;
        AbstractPower deconstructionPower = PowerHandler.findPower(m, DeconstructionPower.class);
        if(deconstructionPower != null){
            deconstructionAmount=((DeconstructionPower) deconstructionPower).getAmount();
        }
        AbstractDungeon.actionManager.addToBottom(
                new DrawCardAction(p,magicNumber+deconstructionAmount));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_DRAW);
            initializeDescription();
        }
    }
}
