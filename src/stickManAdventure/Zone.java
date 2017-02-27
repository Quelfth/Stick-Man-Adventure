package stickManAdventure;

import java.awt.*;
import java.util.TreeSet;

public class Zone {
    int x1 = 0;
    int x2 = 0;
    int y1 = 0;
    int y2 = 0;
    int type = 0;
    int metaType = 0;
    int adData = 0;
    static StickMan s = null;
    int height = 0;
    int width = 0;
    int gx1 = 0;
    int gx2 = 0;
    int gy1 = 0;
    int gy2 = 0;
    private int func0;
    DamageIndicator indicator = new DamageIndicator(0, 0, 0, null);

    public void setAdData(int adData) {
        this.adData = adData;
    }

    public Zone(int _x1, int _y1, int _x2, int _y2, int _type, int _metaType) {
        x1 = _x1;
        y1 = _y1;
        x2 = _x2;
        y2 = _y2;
        type = _type;
        metaType = _metaType;
        width = x2 - x1;
        height = y1 - y2;
    }

    //for mapping collision on normal objects
    private void mapHorizontalCollision(StickMan s) {
        for (int i = s.k.y2(); i < s.k.y; i++) {
            if (i < y1 && i > y2) {
                if (s.k.x - x2 >= 0) {
                    s.zonesLeft.add(s.k.x - x2);
                }
                if (x1 - s.k.x2()
                        >= 0) {
                    s.zonesRight.add(x1 - s.k.x2()
                    );
                }
            }
        }
    }

    private TreeSet<Integer>[] getHorizontalCollision(Entity s) {
        TreeSet<Integer>[] rets = new TreeSet[2];
        for (int i = s.k.y2(); i > s.k.y; i--) {
            if (i < y1 && i > y2) {
                if (s.k.x - x2 >= 0) {
                    rets[0].add(s.k.x - x2);
                }
                if (x1 - s.k.x2()
                        >= 0) {
                    rets[1].add(x1 - s.k.x2()
                    );
                }
            }
        }
        return rets;
    }

    private void mapVerticalCollision(StickMan s) {
        for (int i = s.k.x; i < s.k.x2()
                ; i++) {
            if (i > x1 && i < x2) {
                if (s.k.y2() - y1 >= 0) {
                    s.zonesUp.add(s.k.y2() - y1);
                }
                if (y2 - s.k.y >= 0) {
                    s.zonesDown.add(y2 - s.k.y);
                }
            }
        }
    }

    private TreeSet<Integer>[] getVerticalCollision(Entity s) {
        TreeSet<Integer>[] rets = new TreeSet[2];
        for (int i = s.k.x; i < s.k.x2()
                ; i++) {
            if (i > x1 && i < x2) {
                if (s.k.y2() - y1 >= 0) {
                    rets[1].add(s.k.y2() - y1);
                }
                if (y2 - s.k.y >= 0) {
                    rets[0].add(y2 - s.k.y);
                }
            }
        }
        return rets;
    }

    //For mapping collision on one way platforms
    private void mapTopCollision(StickMan s) {
        for (int i = s.k.x; i < s.k.x2()
                ; i++) {
            if (i > x1 && i < x2) {
                if (y2 - s.k.y >= 0) {
                    s.zonesDown.add(y2 - s.k.y);
                }
            }
        }
    }

    private void mapBottomCollision(StickMan s) {
        for (int i = s.k.x; i < s.k.x2()
                ; i++) {
            if (i > x1 && i < x2) {
                if (s.k.y2() - y1 >= 0) {
                    s.zonesUp.add(s.k.y2() - y1);
                }
            }
        }
    }

    private void mapLeftCollision(StickMan s) {
        for (int i = s.k.y2(); i < s.k.y; i++) {
            if (i < y1 && i > y2) {
                if (x1 - s.k.x2()
                        >= 0) {
                    s.zonesRight.add(x1 - s.k.x2()
                    );
                }
            }
        }
    }

    private void mapRightCollision(StickMan s) {
        for (int i = s.k.y2(); i < s.k.y; i++) {
            if (i < y1 && i > y2) {
                if (s.k.x - x2 >= 0) {
                    s.zonesLeft.add(s.k.x - x2);
                }
            }
        }
    }

    //Calls mapHorizontalCollision and mapVerticalCollision
    private void mapCollision() {
        mapVerticalCollision(s);
        mapHorizontalCollision(s);
    }

    public TreeSet<Integer>[] getCollision(Entity e) {
        TreeSet<Integer>[] rets = new TreeSet[4];
        for (int i : getVerticalCollision(e)[0])
            rets[0].add(i);
        for(int i : getHorizontalCollision(e)[0])
            rets[1].add(i);
        for(int i : getVerticalCollision(e)[1])
            rets[2].add(i);
        for(int i : getHorizontalCollision(e)[1])
            rets[3].add(i);
        return rets;
    }

    //For canceling out motion
    private void mapUpwardMotionCancel() {
        if (s.k.y2() < y1 && detHIntersect() && s.velY > 0 && !(s.k.y2() < y2)) {
            s.velY = 0;
        }
    }

    private void mapDownwardMotionCancel() {
        if (s.k.y > y2 && detHIntersect() && s.velY < 1 && !(s.k.y > y1)) {
            s.velY = 1;
        }
    }

    private void mapVerticalMotionCancel() {
        mapUpwardMotionCancel();
        mapDownwardMotionCancel();
    }

    //For detecting whether this zone is intersecting the player
    public boolean detVIntersect() {
        for (int i = s.k.y2(); i < s.k.y; i++) {
            if (i < y1 && i > y2) {
                return true;
            }
        }
        return false;
    }

    public boolean detHIntersect() {
        for (int i = s.k.x; i < s.k.x2()
                ; i++) {
            if (i > x1 && i < x2) {
                return true;
            }
        }
        return false;
    }

    public boolean detIntersect() {
        return detHIntersect() && detVIntersect();
    }

    //For mapping a zone of damage which will injure the player
    private void mapDamage(int damage, int velocityV, int velocityH) {
        if (detIntersect()) {
            s.hp -= damage;
            s.invisibilityFrames = 20;
            s.extraVel += velocityH;
            s.velY += velocityV;
            indicator.add(s.k.xC(), s.k.yC(), damage, Color.RED);
        }
    }

    private void mapDamage(int damage) {
        mapDamage(damage, 0, 0);
    }

    //For setting the player
    public static void setP1(StickMan s) {
        Zone.s = s;
    }

    //For mapping functions onto this zone
    @SuppressWarnings("unused")
    private void mapFunction(int id) {
        mapFunction(id, 0);
    }

    private void mapFunction(int id, int metaId) {
        mapFunction(id, metaId, 0);
    }

    private void mapFunction(int id, int metaId, int adData) {
        if (detIntersect()) {
            switch (id) {
                case 0:
                    switch (metaId) {
                        case -1:
                            if (s.hp > 0 && (detFullIntersect() && s.vkw && !Adventure.lastWCheck)) {
                                Adventure.stage--;
                                s.respawn();
                            }
                            break;
                        case 0:
                            // if you can go through the door && pressing w
                            if (s.hp > 0 && ((Adventure.stage > 0
                                    && (detFullIntersect() && s.vkw && Adventure.lastWCheck))
                                    || (Adventure.stage <= 0 && (s.onFloor || (detFullIntersect() && s.onWall))))) {
                                // go through the door
                                Adventure.stage++;
                                s.respawn();
                            }
                            break;
                        case 2:
                            if (s.hp > 0 && detFullIntersect())
                                func0 = 1;
                            if (func0 == 1 && s.vkw && Adventure.lastWCheck) {
                                Adventure.stage = adData;
                                s.respawn();
                            }
                    }
                    break;
            }
        }
    }

    //For detecting whether the player is completely within this zone
    private boolean detFullIntersect() {
        return s.k.y <= y1 && s.k.y2() >= y2 && s.k.x >= x1 && s.k.x2()
                <= x2;
    }

    //Update
    public void update() {
        indicator.update();
        switch (type) {
            case -2:
                switch (metaType) {
                    case 0:
                        mapDamage(5, 20, 0);
                        break;
                    case 1:
                        mapDamage(5, 0, 20);
                        break;
                    case 2:
                        mapDamage(5, -20, 0);
                        break;
                    case 3:
                        mapDamage(5, 0, -20);
                        break;
                }
            case -1:
                switch (metaType) {
                    case 0:
                        mapDamage(9999);
                        break;
                    case 1:
                        mapDamage(9999, 20, 0);
                        break;
                }

                break;
            case 1:
                mapCollision();
                mapVerticalMotionCancel();
                break;
            case 2:
                switch (metaType) {
                    case 0:
                        if (!s.vks) {
                            mapTopCollision(s);
                        }
                        break;
                    case 1:
                        if (!s.vkd) {
                            mapLeftCollision(s);
                        }
                        break;
                    case 2:
                        if (!s.vkw) {
                            mapBottomCollision(s);
                        }
                        break;
                    case 3:
                        if (!s.vka) {
                            mapRightCollision(s);
                        }
                }
                break;
            case 3:
                mapFunction(0, metaType, adData);
                break;
        }
    }

    //For drawing a rectangle within this zone to graphics g
    private void fillZone(Graphics g) {
        g.fillRect(x1 + Adventure.xo, y2 + Adventure.yo, x2 - x1, y1 - y2);
    }

    //paint
    public void paint(Graphics g) {
        indicator.paint(g);
        int[] TriX = null;
        int[] TriY = null;
        TriX = new int[3];
        TriY = new int[3];
        switch (type) {
            case -1:
                g.setColor(new Color(255, 0, 0));
                fillZone(g);
                break;
            case 1:
                g.setColor(new Color(191, 191, 191));
                fillZone(g);
                break;
            case 2:
                switch (metaType) {
                    case 0:
                        g.setColor(new Color(204, 153, 102));
                        g.drawLine(x1 + Adventure.xo, y2 + Adventure.yo, x2 + Adventure.xo, y2 + Adventure.yo);
                        TriX[0] = x1 + Adventure.xo;
                        TriX[1] = x1 + Adventure.xo;
                        TriX[2] = x1 + Adventure.xo + width / 8;
                        TriY[0] = y2 + Adventure.yo;
                        TriY[1] = y2 + Adventure.yo + height / 4;
                        TriY[2] = y2 + Adventure.yo;
                        g.drawPolygon(TriX, TriY, 3);
                        TriX[0] = x2 + Adventure.xo;
                        TriX[1] = x2 + Adventure.xo;
                        TriX[2] = x2 + Adventure.xo - width / 8;
                        g.drawPolygon(TriX, TriY, 3);
                        break;
                    case 1:
                        g.setColor(new Color(204, 153, 102));
                        g.drawLine(x1 + Adventure.xo, y1 + Adventure.yo, x1 + Adventure.xo, y2 + Adventure.yo);
                        TriX[0] = x1 + Adventure.xo;
                        TriX[1] = x1 + Adventure.xo + width / 4;
                        TriX[2] = x1 + Adventure.xo;
                        TriY[0] = y1 + Adventure.yo;
                        TriY[1] = y1 + Adventure.yo;
                        TriY[2] = y1 + Adventure.yo - width / 8;
                        g.drawPolygon(TriX, TriY, 3);
                        TriY[0] = y2 + Adventure.yo;
                        TriY[1] = y2 + Adventure.yo;
                        TriY[2] = y2 + Adventure.yo + width / 8;
                        g.drawPolygon(TriX, TriY, 3);
                        break;
                    case 2:
                        g.setColor(new Color(204, 153, 102));
                        g.drawLine(x1 + Adventure.xo, y1 + Adventure.yo, x2 + Adventure.xo, y1 + Adventure.yo);
                        TriX[0] = x1 + Adventure.xo;
                        TriX[1] = x1 + Adventure.xo;
                        TriX[2] = x1 + Adventure.xo + width / 8;
                        TriY[0] = y2 + Adventure.yo;
                        TriY[1] = y2 + Adventure.yo + height / 4;
                        TriY[2] = y2 + Adventure.yo;
                        g.drawPolygon(TriX, TriY, 3);
                        TriX[0] = x2 + Adventure.xo;
                        TriX[1] = x2 + Adventure.xo;
                        TriX[2] = x2 + Adventure.xo - width / 8;
                        g.drawPolygon(TriX, TriY, 3);
                        break;
                }
                break;
            case 3:
                switch (metaType) {
                    case -1:
                        drawDoor(g, new Color(63, 63, 63), new Color(95, 95, 95), new Color(127, 127, 127));
                        break;
                    case 0:
                        drawDoor(g, new Color(172, 115, 57), new Color(96, 64, 32), new Color(63, 63, 63));
                        break;
                    case 2:
                        if (func0 == 1)
                            drawDoor(g, new Color(255, 192, 0), new Color(192, 144, 0), new Color(255, 255, 0));
                        break;
                }
                break;
        }
    }

    //For drawing a door within the confines of this zone to graphics g
    private void drawDoor(Graphics g, Color main, Color indents, Color knob) {
        g.setColor(main);
        fillZone(g);
        g.setColor(indents);
        g.fillRect(x1 + Adventure.xo + (width / 7), y2 + Adventure.yo + (height / 11), width * 2 / 7, height * 4 / 11);
        g.fillRect(x1 + Adventure.xo + (width * 4 / 7), y2 + Adventure.yo + (height / 11), width * 2 / 7, height * 4 / 11);
        g.fillRect(x1 + Adventure.xo + (width / 7), y2 + Adventure.yo + (height * 6 / 11), width * 2 / 7, height * 4 / 11);
        g.fillRect(x1 + Adventure.xo + (width * 4 / 7), y2 + Adventure.yo + (height * 6 / 11), width * 2 / 7, height * 4 / 11);
        g.setColor(knob);
        g.fillOval(x1 + Adventure.xo + (width * 49 / 56), y2 + Adventure.yo + (height * 6 / 11) + ((height / 2) / 56), width * 6 / 56,
                width * 6 / 56);
    }
}
