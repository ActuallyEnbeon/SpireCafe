package spireCafe.interactables.attractions.scamartist;

import spireCafe.abstracts.AbstractCutscene;
import spireCafe.abstracts.AbstractNPC;
import spireCafe.util.cutsceneStrings.CutsceneStrings;
import spireCafe.util.cutsceneStrings.LocalizedCutsceneStrings;

import static spireCafe.Anniv7Mod.makeID;

public class ScamArtistCutscene extends AbstractCutscene {
    public static final String ID = makeID(ScamArtistCutscene.class.getSimpleName());
    private static final CutsceneStrings cutsceneStrings = LocalizedCutsceneStrings.getCutsceneStrings(ID);

    public ScamArtistCutscene(AbstractNPC character) {
        super(character, cutsceneStrings);
    }
}
