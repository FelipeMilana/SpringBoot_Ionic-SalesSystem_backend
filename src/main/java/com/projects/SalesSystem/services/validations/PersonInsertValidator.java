package com.projects.SalesSystem.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.projects.SalesSystem.controllers.exceptions.FieldMessage;
import com.projects.SalesSystem.entities.dto.PersonDTO;
import com.projects.SalesSystem.services.validations.utils.BR;

public class PersonInsertValidator implements ConstraintValidator<PersonInsert, PersonDTO>{

	@Override
	public void initialize(PersonInsert ann) {
	}
	
	@Override
	public boolean isValid(PersonDTO objDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if(!BR.isValidCPF(objDTO.getCpfCnpj()) && !BR.isValidCNPJ(objDTO.getCpfCnpj())) {
			list.add(new FieldMessage("cpfCnpj", "CPF ou CNPJ inválido"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
