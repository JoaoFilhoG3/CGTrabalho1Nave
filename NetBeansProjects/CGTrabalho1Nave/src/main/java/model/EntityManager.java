package model;

import interfaces.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class EntityManager implements KeyListener {

    private List<Entity> lEntities = new ArrayList<Entity>();
    private static EntityManager entityManager = new EntityManager();

    private EntityManager() {

    }

    public static EntityManager getInstance() {
        return entityManager;
    }

    public void add(Entity object) {
        this.lEntities.add(object);
        int i = 1;
    }

    public boolean remove(Entity object) {
        return this.lEntities.remove(object);
    }

    public void render() {
        for (int i = 0; i < lEntities.size(); i++) {
            lEntities.get(i).draw();
        }
    }

    public void update() {
        for (int i = 0; i < lEntities.size(); i++) {
            if (!lEntities.get(i).update()) {
                remove(lEntities.get(i));
            }
        }
    }

    @Override
    public void onKeyEvent(int key, int action) {

    }

    public List<Entity> getlEntities() {
        return lEntities;
    }

}
