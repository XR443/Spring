package com.github.hardlolmaster.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hardlolmaster.controller.ResponseObject;
import com.github.hardlolmaster.domain.PropertyInsuranceContract;
import com.github.hardlolmaster.repository.ContractRepository;
import com.github.hardlolmaster.utils.Validator;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.github.hardlolmaster.controller.CommonResponseObject.INCORRECT_INPUT;
import static com.github.hardlolmaster.utils.GetCommandGroupKey.getFor;

@Component
@Transactional
public class SaveContractAction extends AbstractAction
{
    private final ContractRepository contractRepository;
    private final MutableAclService aclService;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    @Autowired
    public SaveContractAction(ContractRepository contractRepository,
            MutableAclService aclService,
            @Qualifier("saveContractValidator") Validator validator,
            ObjectMapper objectMapper)
    {
        this.contractRepository = contractRepository;
        this.aclService = aclService;
        this.validator = validator;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseObject<?> execute(Object input)
    {
        final PropertyInsuranceContract contract = objectMapper.convertValue(input, PropertyInsuranceContract.class);
        if (!validator.valid(contract))
            return INCORRECT_INPUT;

        PropertyInsuranceContract save = null;
        if ((contract.getId() == null && !contractRepository.existsByNumber(contract.getNumber())))
            save = contractRepository.save(contract);

        if ((contract.getId() == null && contractRepository.existsByNumber(contract.getNumber())))
            throw new RuntimeException("Contract with number " + contract.getNumber() + " already existing");

        Optional<PropertyInsuranceContract> byId = contractRepository.findById(contract.getId());
        if (byId.isEmpty())
            throw new RuntimeException("Contract with id " + contract.getId() + " not existing");

        if (contract.getNumber().equals(byId.get().getNumber()))
            save = contractRepository.save(contract);
        else if (!contractRepository.existsByNumber(contract.getNumber()))
            save = contractRepository.save(contract);
        if (save == null)
            throw new RuntimeException("Can't save the contract. Contract with number " + contract.getNumber() + " already existing");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Sid owner = new PrincipalSid(auth);
        GrantedAuthoritySid role_manager = new GrantedAuthoritySid("ROLE_MANAGER");
        Sid role_admin = new GrantedAuthoritySid("ROLE_ADMIN");
        ObjectIdentity oid = new ObjectIdentityImpl(PropertyInsuranceContract.class,
                save.getId());
        try
        {
            MutableAcl acl = aclService.createAcl(oid);
            acl.setOwner(owner);
            acl.insertAce(acl.getEntries().size(), BasePermission.READ, owner, true);
            acl.insertAce(acl.getEntries().size(), BasePermission.READ, role_admin, true);
            acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, role_admin, true);
            aclService.updateAcl(acl);
        }
        catch (AlreadyExistsException alreadyExistsException)
        {
//            alreadyExistsException.printStackTrace();
        }

        return new ResponseObject<>(true, save);
    }
}
