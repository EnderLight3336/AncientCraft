package me.enderlight3336.ancientcraft.metadata;

import com.alibaba.fastjson2.JSONObject;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import oshi.annotation.concurrent.ThreadSafe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class Sack implements ACInventoryHolder {
    @ThreadSafe
    private final Object2IntOpenHashMap<Material> map;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Inventory inventory = Bukkit.createInventory(this, 54);
    {
        inventory.setItem(45, );
        inventory.setItem(53, );
    }
    public Sack() {
        map = new Object2IntOpenHashMap<>();
    }
    public Sack(JSONObject json) {
        JSONObject sackJSON = json.getJSONObject("sack");
        map = new Object2IntOpenHashMap<>(sackJSON.size());
        sackJSON.forEach((str, obj) -> map.put(Material.valueOf(str), (int) obj));
    }
    public boolean putItem(ItemStack item) throws InterruptedException, TimeoutException {
        if (lock.writeLock().tryLock(1, TimeUnit.SECONDS)) {
            final var ref = new Object() {
                boolean ret;
            };
            map.computeInt(item.getType(), (material, aInt) -> {
                int amount = item.getAmount();
                if (aInt == null) {
                    ref.ret = true;
                    return amount;
                } else {
                    int result = aInt + amount;
                    // HD 2-12 Overflow iff both arguments have the opposite sign of the result
                    if (((aInt ^ result) & (amount ^ result)) < 0) {
                        ref.ret = false;
                        return aInt;
                    } else {
                        ref.ret = true;
                        return result;
                    }
                }
            });
            lock.writeLock().unlock();
            return ref.ret;
        } else throw new TimeoutException("Cannot get write lock after 1 second!");
    }
    public int getItemAmount(Material material) {
        return map.getOrDefault(material, 0);
    }
    public void setItemAmount(Material material, int amount) {
        lock.writeLock().lock();
        map.put(material, amount);
        lock.writeLock().unlock();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        lock.writeLock().lock();
        ObjectIterator<Object2IntMap.Entry<Material>> iterator = map.object2IntEntrySet().fastIterator();
        while (iterator.hasNext()) {
            Object2IntMap.Entry<Material> entry = iterator.next();
            sb.append("\"").append(entry.getKey().toString()).append("\":").append(entry.getIntValue()).append(",");
        }
        lock.writeLock().unlock();
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Sack && map.equals(((Sack) obj).map);
    }
}
