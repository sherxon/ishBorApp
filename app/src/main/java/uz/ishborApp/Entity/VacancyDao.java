package uz.ishborApp.Entity;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import uz.ishborApp.Entity.Vacancy;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "VACANCY".
*/
public class VacancyDao extends AbstractDao<Vacancy, Long> {

    public static final String TABLENAME = "VACANCY";

    /**
     * Properties of entity Vacancy.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Price = new Property(1, String.class, "price", false, "PRICE");
        public final static Property CompanyName = new Property(2, String.class, "companyName", false, "COMPANY_NAME");
        public final static Property Position = new Property(3, String.class, "position", false, "POSITION");
        public final static Property Descc = new Property(4, String.class, "descc", false, "DESCC");
        public final static Property StDate = new Property(5, String.class, "stDate", false, "ST_DATE");
        public final static Property CategoryId = new Property(6, long.class, "categoryId", false, "CATEGORY_ID");
    };

    private DaoSession daoSession;

    private Query<Vacancy> category_VacancyListQuery;

    public VacancyDao(DaoConfig config) {
        super(config);
    }
    
    public VacancyDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"VACANCY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PRICE\" TEXT," + // 1: price
                "\"COMPANY_NAME\" TEXT," + // 2: companyName
                "\"POSITION\" TEXT," + // 3: position
                "\"DESCC\" TEXT," + // 4: descc
                "\"ST_DATE\" TEXT," + // 5: stDate
                "\"CATEGORY_ID\" INTEGER NOT NULL );"); // 6: categoryId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"VACANCY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Vacancy entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String price = entity.getPrice();
        if (price != null) {
            stmt.bindString(2, price);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(3, companyName);
        }
 
        String position = entity.getPosition();
        if (position != null) {
            stmt.bindString(4, position);
        }
 
        String descc = entity.getDescc();
        if (descc != null) {
            stmt.bindString(5, descc);
        }
 
        String stDate = entity.getStDate();
        if (stDate != null) {
            stmt.bindString(6, stDate);
        }
        stmt.bindLong(7, entity.getCategoryId());
    }

    @Override
    protected void attachEntity(Vacancy entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Vacancy readEntity(Cursor cursor, int offset) {
        Vacancy entity = new Vacancy( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // price
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // companyName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // position
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // descc
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // stDate
            cursor.getLong(offset + 6) // categoryId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Vacancy entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPrice(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCompanyName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPosition(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDescc(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setStDate(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCategoryId(cursor.getLong(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Vacancy entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Vacancy entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "vacancyList" to-many relationship of Category. */
    public List<Vacancy> _queryCategory_VacancyList(long categoryId) {
        synchronized (this) {
            if (category_VacancyListQuery == null) {
                QueryBuilder<Vacancy> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.CategoryId.eq(null));
                category_VacancyListQuery = queryBuilder.build();
            }
        }
        Query<Vacancy> query = category_VacancyListQuery.forCurrentThread();
        query.setParameter(0, categoryId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getCategoryDao().getAllColumns());
            builder.append(" FROM VACANCY T");
            builder.append(" LEFT JOIN CATEGORY T0 ON T.\"CATEGORY_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Vacancy loadCurrentDeep(Cursor cursor, boolean lock) {
        Vacancy entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Category category = loadCurrentOther(daoSession.getCategoryDao(), cursor, offset);
         if(category != null) {
            entity.setCategory(category);
        }

        return entity;    
    }

    public Vacancy loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Vacancy> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Vacancy> list = new ArrayList<Vacancy>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Vacancy> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Vacancy> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
