package velKoz.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import velKoz.VelKozMod;
import velKoz.powers.DualDeconstuctionPower;

import static velKoz.VelKozMod.makePowerCardPath;

public class DualDeconstrucion extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = VelKozMod.makeID(DualDeconstrucion.class.getSimpleName());
    public static final String IMG = makePowerCardPath("Power.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = velKoz.characters.VelKoz.Enums.COLOR_GRAY;

    private static final int COST = 3;
//    private static final int UPGRADED_COST = 2;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    // /STAT DECLARATION/


    public DualDeconstrucion() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new DualDeconstuctionPower(p, p, magicNumber), magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
//            upgradeBaseCost(UPGRADED_COST);
//          升级时加上固有
            initializeDescription();
        }
    }
}