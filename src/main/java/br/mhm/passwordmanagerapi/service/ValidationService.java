package br.mhm.passwordmanagerapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class ValidationService {

	@Autowired private SpringValidatorAdapter validator;
	@Autowired private ObjectMapper mapper;

	public ObjectNode validate(Object target) {
		BindingResult result = new BeanPropertyBindingResult(target, "");

		validator.validate(target, result);

		if (!result.hasErrors()) {
			return null;
		}

		List<FieldError> errors;

		errors = result.getFieldErrors();

		if (errors == null || errors.isEmpty()) {
			return null;
		}

		ObjectNode rejected = mapper.createObjectNode();

		for (FieldError error : errors) {
			addError(rejected, error.getField(), error.getCode(), error.getRejectedValue());
		}

		return rejected;
	}
	
    public ObjectNode addError(ObjectNode rejected, String fieldName, String code, Object rejectedValue)
    {
        String[] tokens = fieldName.split("[.]");
        ObjectNode node = rejected;

        for (int i = 0; i < tokens.length; ++i)
        {
            if (i < tokens.length - 1)
            {
                if (node.has(tokens[i])) {
                    node = (ObjectNode) node.get(tokens[i]);
                }
                else {
                    node = node.putObject(tokens[i]);
                }
            }
            else
            {
                node = node.putObject(tokens[i]);
                node.put("value", rejectedValue != null ? rejectedValue.toString() : null);

                if ("unique".equalsIgnoreCase(code)) {
                    node.put("code", code);
                }
            }
        }

        return node;
    }
}
