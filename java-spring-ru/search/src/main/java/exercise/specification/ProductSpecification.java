package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

@Component
public class ProductSpecification {
    public Specification<Product> build(ProductParamsDTO params) {
        return withTitleContains(params.getTitleCont())
                .and(withCategoryId(params.getCategoryId()))
                .and(withPriceLessThan(params.getPriceLt()))
                .and(withPriceGreaterThan(params.getPriceGt()))
                .and(withRatingGreaterThan(params.getRatingGt()));
    }

    private Specification<Product> withTitleContains(String title) {
        return (root, query, cb) -> title == null ? cb.conjunction() : cb.like(root.get("title"), title);
    }
    private Specification<Product> withCategoryId(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? cb.conjunction() : cb.equal(root.get("category").get("id"), categoryId);
    }
    private Specification<Product> withPriceLessThan(Integer lessThan) {
        return (root, query, cb) -> lessThan == null ? cb.conjunction() : cb.lessThan(root.get("price"), lessThan);

    }
    private Specification<Product> withPriceGreaterThan(Integer greaterThan) {
        return (root, query, cb) -> greaterThan == null ? cb.conjunction() : cb.greaterThan(root.get("price"), greaterThan);
    }
    private Specification<Product> withRatingGreaterThan(Double greaterThan) {
        return (root, query, cb) -> greaterThan == null ? cb.conjunction() : cb.greaterThan(root.get("rating"), greaterThan);


    }
}