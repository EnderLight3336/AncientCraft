package me.enderlight3336.ancientcraft.item.instance.part.base;

public final class EnchantementPart extends ItemMetaPart {
    private final Enchantement enc;
    private final short[] value;
    public EnchantementPart(JSONObject json) {
        super(json);

        this.enc = Enchantement.valueOf(json.getString("enchantement"));
    }

    @Override
    public void acceptMeta(ItemMeta im, int current) {
        im.addEnchant(this.enc, value[current], true);
    }

    @Override
    public boolean canApply(int current) {
        return current < value.length;
    }
}