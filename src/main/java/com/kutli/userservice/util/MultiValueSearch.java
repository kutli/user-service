package com.kutli.userservice.util;

import com.kutli.userservice.customException.CustomException;
import com.kutli.userservice.customException.ErrorMessages;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MultiValueSearch {

    protected Set<String> filterValidValues;
    private Set<String> pagingRequestParams;

    MultiValueSearch() {
        this.setBaseValidValues();
    }

    /**
     * Define the common valid params in multi value search request
     */
    private void setBaseValidValues() {
        filterValidValues = new HashSet<>();
        filterValidValues.add("page");
        filterValidValues.add("size");
        filterValidValues.add("sort");
        pagingRequestParams = new HashSet<>(filterValidValues);
    }

    public void setFilterValidValues(String... values) {
        for(String val: values) {
            this.filterValidValues.add(val);
        }
    }

    /**
     * Validate the values passed for filtering
     * @param filters
     * @throws CustomException
     */
    public void filterValuesValidation(Map<String, String> filters) throws CustomException {
        if(this.filterValidValues.size() <= 3) {
            throw CustomException.builder()
                    .httpStatus(HttpStatus.NOT_IMPLEMENTED)
                    .errorMessage(ErrorMessages.ERROR_UNIMPLEMENTED_METHOD)
                    .build();
        }
        for(String pagingParam : pagingRequestParams) {
            filters.remove(pagingParam);
        }
        if(!this.filterValidValues.containsAll(filters.keySet())) {
            Set<String> invalidFields = filters.keySet();
            invalidFields.removeAll(filterValidValues);
            throw CustomException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorMessage(ErrorMessages.INVALID_QUERY_PARAMS)
                    .field(String.join(", ", invalidFields))
                    .build();
        }
    }
}
