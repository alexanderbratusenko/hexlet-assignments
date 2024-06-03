package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ProductMapper {

    @Mapping(source = "vendorCode", target = "barcode")
    @Mapping(source = "title", target = "name")
    @Mapping(source = "price", target = "cost")
    public abstract Product map(ProductCreateDTO dto);

    @Mapping(target = "vendorCode", source = "barcode")
    @Mapping(source = "name", target = "title")
    @Mapping(source = "cost", target = "price")
    public abstract ProductDTO map(Product product);

    @Mapping(source = "price", target = "cost")
    public abstract void map(ProductUpdateDTO dto, @MappingTarget Product product);
}