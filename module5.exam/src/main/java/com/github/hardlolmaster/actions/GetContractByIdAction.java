package com.github.hardlolmaster.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.repository.ContractRepository;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.github.hardlolmaster.controller.CommonResponseObject.SOMETHING_WENT_WRONG;

@Component
public class GetContractByIdAction extends AbstractAction {

    private final ContractRepository contractRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public GetContractByIdAction(ContractRepository contractRepository, ObjectMapper objectMapper) {
        this.contractRepository = contractRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseObject<?> execute(Object input) {
        if (input == null)
            return new ResponseObject<>(false, "Не представлен id");
        final Long id = objectMapper.convertValue(input, Long.class);
        return new HystrixCommand<ResponseObject<?>>(HystrixCommandGroupKey.Factory.asKey("GetContractById")) {

            @Override
            protected ResponseObject<?> run() {
                Optional<PropertyInsuranceContract> byId = contractRepository.findById(id);
                if (byId.isEmpty())
                    return new ResponseObject<>(false, "Объект с таким id не найден");
                return new ResponseObject<>(true, byId.get());
            }

            @Override
            protected ResponseObject<?> getFallback() {
                return SOMETHING_WENT_WRONG;
            }
        }.execute();
    }
}
