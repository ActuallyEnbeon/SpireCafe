package spireCafe.interactables.merchants.secretshop;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import spireCafe.Anniv7Mod;
import spireCafe.abstracts.AbstractMerchant;
import spireCafe.util.TexLoader;

public class SecretShopMerchant extends AbstractMerchant {

    private static final float TOP_ROW_Y = 760.0F * Settings.yScale;
    private static final float BOTTOM_ROW_Y = 337.0F * Settings.yScale;
    private static final float DRAW_START_X = Settings.WIDTH * 0.16F;
    
    private static final String ID = SecretShopMerchant.class.getSimpleName();
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(Anniv7Mod.makeID(ID));
    private static final Texture RUG_TEXTURE = TexLoader.getTexture(Anniv7Mod.makeMerchantPath("secretshop/rug.png"));

    public ArrayList<AbstractCard> cards = new ArrayList<>();

    public ArrayList<AbstractRelic> relics = new ArrayList<>();
    public ArrayList<AbstractPotion> potions = new ArrayList<>();
    public boolean identifyMode;
    public IdentifyArticle idArticle;

    public SecretShopMerchant(float animX, float animY) {
        super(animX, animY, 160.0f, 200.0f);
        this.name = characterStrings.NAMES[0];
        this.authors = "Coda";
        background = new TextureRegion(RUG_TEXTURE);
        this.img = TexLoader.getTexture(Anniv7Mod.makeMerchantPath("example/merchant.png"));
        this.identifyMode = false;
        
    }
    
    @Override
    protected void rollShop() {
        float xPos;
        float yPos;

        xPos = Settings.WIDTH * 0.75F;
        yPos = 164.0F * Settings.yScale;
        this.idArticle = new IdentifyArticle(this, xPos, yPos);
        articles.add(this.idArticle);

        initCards();
        int cost;
        int tmp = (int)(Settings.WIDTH - DRAW_START_X * 2.0F - AbstractCard.IMG_WIDTH_S * 5.0F) / 4; // ???
        float padX = (int)(tmp + AbstractCard.IMG_WIDTH_S) + 10.0F * Settings.scale;
        for (int i = 0; i < 5; i++) {
            xPos = DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0F + padX * i;
            yPos = TOP_ROW_Y;
            UnidentifiedCard unidentifiedCard = new UnidentifiedCard();
            unidentifiedCard.hiddenCard = this.cards.get(i);
            unidentifiedCard.targetDrawScale = 0.75F;
            cost = setCardBasePrice();
            IdentifyCardArticle tmpArticle = new IdentifyCardArticle(this, this.idArticle, xPos, yPos, unidentifiedCard, cost);
            articles.add(tmpArticle);
        }
        for (int i = 0; i < 2; i++) {
            xPos = DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0F + padX * i;
            yPos = BOTTOM_ROW_Y;
            UnidentifiedCard unidentifiedCard = new UnidentifiedCard();
            unidentifiedCard.hiddenCard = this.cards.get(i + 5);
            unidentifiedCard.targetDrawScale = 0.75F;
            cost = setCardBasePrice();
            IdentifyCardArticle tmpArticle = new IdentifyCardArticle(this, this.idArticle, xPos, yPos, unidentifiedCard, cost);
            articles.add(tmpArticle);
        }

        initRelics();
        for (int i = 0; i < relics.size(); i++) {
            IdentifyRelicArticle tmpArticle = new IdentifyRelicArticle(this, i, this.relics.get(i), setRelicBasePrice());
            articles.add(tmpArticle);
        }

    }
        
    private void initCards() {
        AbstractCard c;
        ArrayList<CardType> tmpType = new ArrayList<>();
        tmpType.add(CardType.ATTACK);
        tmpType.add(CardType.SKILL);
        tmpType.add(CardType.POWER);
        
        for (int i = 0; i < 5; i++) {
            Collections.shuffle(tmpType, new java.util.Random(AbstractDungeon.merchantRng.randomLong()));
            c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), tmpType.get(0), true).makeCopy();
            this.cards.add(c);
        }
        
        for (int i = 0; i < 2; i++) {
            CardRarity tmpRarity = CardRarity.UNCOMMON;
            if (AbstractDungeon.merchantRng.random() < AbstractDungeon.colorlessRareChance){
                tmpRarity = CardRarity.RARE;
            }
            c = AbstractDungeon.getColorlessCardFromPool(tmpRarity).makeCopy();
            this.cards.add(c);
        }

        Collections.shuffle(this.cards, new java.util.Random(AbstractDungeon.merchantRng.randomLong()));
    }

    private void initRelics() {
        AbstractRelic r;
        for (int i = 0; i < 3; i++) {
            if (i != 2){ 
                int roll = AbstractDungeon.merchantRng.random(99);
                RelicTier tmpTier = RelicTier.COMMON;
                if (roll >= 48) {
                    tmpTier = RelicTier.UNCOMMON;
                }
                if (roll >= 82) {
                    tmpTier = RelicTier.RARE;
                }
                r = AbstractDungeon.returnRandomRelicEnd(tmpTier);
            } else {
                r = AbstractDungeon.returnRandomRelicEnd(AbstractRelic.RelicTier.SHOP);
            }
            this.relics.add(r);
        }
        Collections.shuffle(this.relics, new java.util.Random(AbstractDungeon.merchantRng.randomLong()));
    }

    private int setCardBasePrice() {
        int ret = (int)(AbstractCard.getPrice(CardRarity.UNCOMMON) * AbstractDungeon.merchantRng.random(0.7F, 1.2F));
        return ret;
    }

    private int setRelicBasePrice() {
        int ret = (int)(AbstractCard.getPrice(CardRarity.UNCOMMON) * AbstractDungeon.merchantRng.random(0.7F, 1.2F));
        return ret;
    }
    
}
