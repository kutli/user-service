package com.kutli.userservice.generics;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class Specs<T> {

    private static final List<String> FALSE_VALUES = Arrays.asList("f","fa","fal","fals","false");

    private static final List<String> TRUE_VALUES = Arrays.asList("t","tr","tru","true");

    /**
     * Generic filter using a map. If any field in the map is not in the Entity T,
     * it will throw an IllegalArgumentException
     * <p>
     *
     * @param filters the map with filters. This map has the fields on the keys and
     *                the search values on the mapped values(field, search value).
     *                Example of a valid map: <blockquote> (firstName,
     *                Steph,Demetrio)<br>
     *                (site.name, GDL) </blockquote>
     * @return the Specification to filter.
     */
    public Specification<T> filterWithMap(Map<String, String> filters) {
        Specification<T> specs = null;
        clearFilters(filters);
        Set<String> keys = filters.keySet();
        Iterator<String> itr = keys.iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            String value = filters.get(key);
            if(key.equals("active")||key.endsWith(".active")) {
                if(isTrueValue(value)) {
                    value = "1";
                }else if(isFalseValue(value)) {
                    value = "0";
                }
            }
            if (value != null && !value.isEmpty())
                specs = Specification.where(specs).and(findByFieldLike(key, value));
        }
        return specs;
    }

    private static boolean isFalseValue(String value) {
        return FALSE_VALUES.stream().anyMatch(falseValue -> value.equalsIgnoreCase(falseValue));
    }

    private static boolean isTrueValue(String value) {
        return TRUE_VALUES.stream().anyMatch(trueValue -> value.equalsIgnoreCase(trueValue));
    }

    /**
     * Generic filter by one field. Allows filter by multiple values.
     *
     * @param field      the field to filter. It can be an inner property. This
     *                   properties should be related to the entity T. <blockquote>
     *                   Examples of fields:
     *                   <li>firstname</li>
     *                   <li>site.name</li> </blockquote>
     * @param searchTerm the value to search, it can have multiple values separated
     *                   by commas.
     * @return a Specification to filter.
     */
    public Specification<T> findByFieldLike(String field, String searchTerm) {
        return (root, query, cb) -> {
            StringTokenizer stk = new StringTokenizer(searchTerm, ",");
            Predicate predicate = cb.like(getPath(root, field).as(String.class),
                    getContainsLikePattern(stk.nextToken()));
            while (stk.hasMoreElements()) {
                predicate = cb.or(predicate,
                        cb.like(getPath(root, field).as(String.class), getContainsLikePattern(stk.nextToken())));
            }
            return predicate;
        };
    }

    /**
     * Specification to filter by not deleted elements.
     *
     * @return the Specification.
     */
    public Specification<T> isNotDeleted() {
        return (root, query, cb) -> cb.equal(root.get("isDeleted"), false);
    }

    /**
     * Filter T that belong to any of the given Set of ID's
     *
     * @param ids Set of Long ids
     *
     * @return {@link Specification} of the given object
     */
    public Specification<T> filterByIds(Set<Long> ids) {
        return (root, query, cb) -> root.get("id").in(ids);
    }

    /**
     * Get the path. Helps to get the path for inner fields. Ex: site.name
     *
     * @param root  the Root for the Entity T.
     * @param field the field to get the Path.
     * @return the Path.
     */
    private Path<Object> getPath(Root<T> root, String field) {
        StringTokenizer stk = new StringTokenizer(field, ".");
        Path<Object> path = root.get(stk.nextToken());
        while (stk.hasMoreElements()) {
            path = path.get(stk.nextToken());
        }
        return path;
    }

    /**
     * Remove the values from pagination Request.
     *
     * @param filters
     */
    private static void clearFilters(Map<String, String> filters) {
        filters.remove("page");
        filters.remove("sort");
        filters.remove("size");
    }

    private static String getContainsLikePattern(String searchTerm) {
        return "%" + searchTerm + "%";
    }
}
