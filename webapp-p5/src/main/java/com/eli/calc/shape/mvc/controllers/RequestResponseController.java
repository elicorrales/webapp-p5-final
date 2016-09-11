package com.eli.calc.shape.mvc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eli.calc.shape.domain.CalculationRequest;
import com.eli.calc.shape.domain.CalculationResult;
import com.eli.calc.shape.model.CalcType;
import com.eli.calc.shape.model.ShapeName;
import com.eli.calc.shape.service.ShapeCalculatorService;

@Controller
public class RequestResponseController {

	@Autowired
	private ShapeCalculatorService calculator;
	

	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView welcomeTheSlash() {

    	System.err.println("\n\n\nWelcome: The / (slash) ; Index page\n\n\n");

		return new ModelAndView("/index","message","Slash Index Page: dynamic message from Controller - not used at the moment");
	}


	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView welcomeTheIndex() {

    	System.err.println("\n\n\nWelcome2: The /index Index page\n\n\n");

		return new ModelAndView("/index","message","Index Index Page: dynamic message from Controller - not used at the moment");
	}


    @RequestMapping(value="/newreq", method = RequestMethod.GET)
    public String newreq(Map<String, Object> model) {
    	
    	System.err.println("\n\n\nINSIDE Controller newreq()\n\n\n");

    	boolean isError = false;
    	StringBuffer errors = new StringBuffer("There are errors:");
    	try {

    		CalculationRequest calcRequestForm = new CalculationRequest();
    		model.put("calcRequestForm", calcRequestForm);
         
    	} catch (Exception e) {
    		errors.append("creating new request: "+e.getMessage());
    		isError = true;
    	}

    	if (populateLists(model,errors)) isError = true;

    	if (isError) {
    		model.put("error",errors.toString());
    	}

        return "newreq";
    }

    @RequestMapping(value="/newreq", method = RequestMethod.POST)
    public String processRequest(
    		@ModelAttribute("calcRequestForm") CalculationRequest calcRequestForm,
            Map<String, Object> model
            ) {
         
    	System.err.println("\n\n\nINSIDE Controller processRequest()\n\n\n");

    	boolean isError = false;
    	StringBuffer errors = new StringBuffer("There are errors:");
    	try {
    	
    		this.calculator.queueCalculationRequest(
    			calcRequestForm.getShapeName(),
    			calcRequestForm.getCalcType(),
    			calcRequestForm.getDimension());

    	} catch (Exception e) {
    		errors.append("There was an error queueing request:"+e.getMessage());
    		isError = true;
    	}

   		if (populateLists(model,errors)) isError = true;

    	if (isError) {
    		model.put("error",errors.toString());
    	} else {
    		model.put("success","Success: Request Queued or Calculated Previously");
    	}

        return "newreq";
    }

    @ExceptionHandler(BindException.class)
    public ModelAndView handleBindException(HttpServletRequest req, BindException e) {
    	
    	System.err.println("\n\n\nINSIDE Controller handleBindingException()\n\n\n");

    	StringBuffer errors = new StringBuffer("There are errors:");

    	List<FieldError> fieldErrors = e.getFieldErrors();
    	for (FieldError fe : fieldErrors) {
    		errors.append(fe.getRejectedValue()+" is not valid for "+fe.getField()+".\n");
    	}

    	ModelAndView mav = new ModelAndView();
    	try {
    		CalculationRequest calcRequestForm = new CalculationRequest();
			mav.addObject("calcRequestForm", calcRequestForm);
         
    	} catch (Exception cre) {
    		errors.append("creating new request: "+cre.getMessage());
    	}

   		populateLists(mav.getModel(),errors);

   		mav.addObject("error",errors.toString());

    	mav.setViewName("newreq");

    	return mav;
    }

 
    private boolean populateLists(Map<String, Object> model, StringBuffer errors) {
    	boolean isError = false;
    	try {

    		List<String> shapeList = new ArrayList<>();
    		for (ShapeName name : ShapeName.values()) {
    			shapeList.add(name.name());
    		}
    		model.put("shapeList", shapeList);

    	} catch (Exception e) {
    		errors.append("Obtaining Shapes: "+e.getMessage());
    		isError = true;
    	}

    	try {

    		List<String> calcTypeList = new ArrayList<>();
    		for (CalcType type : CalcType.values()) {
    			calcTypeList.add(type.name());
    		}
    		model.put("calcTypeList", calcTypeList);

    	} catch (Exception e) {
    		errors.append("Obtaining Calc Types: "+e.getMessage());
    		isError = true;
    	}
    	
    	return isError;
    }
    

	@RequestMapping(value="/pending",method=RequestMethod.GET)
	public ModelAndView pending() {

    	System.err.println("\n\n\nPending Requests\n\n\n");

    	try {
    	
    		List<CalculationRequest> pendingRequests = calculator.getAllPendingRequests();

    		if (null==pendingRequests || pendingRequests.isEmpty()) {
    			return new ModelAndView("/pending","message", "There are NO Pending Requests");
    		}

    		return new ModelAndView("/pending","pendingRequests",pendingRequests);

    	} catch (Exception e) {
    		return new ModelAndView("/pending","error", "There was an error:"+e.getMessage());
    	}
	}

	@RequestMapping(value="/results",method=RequestMethod.GET)
	public ModelAndView results() {

    	System.err.println("\n\n\nCalculated Results\n\n\n");

    	try {

    		List<CalculationResult> calculatedResults = this.calculator.getAllCalculatedResults();

    		if (null==calculatedResults || calculatedResults.isEmpty()) {
    			return new ModelAndView("/results","message", "There are NO Calculated Results Yet");
    		}

    		return new ModelAndView("/results","calculatedResults",calculatedResults);

    	} catch (Exception e) {
    		return new ModelAndView("/results","error", "There was an error:"+e.getMessage());
    	}
	}


	@RequestMapping(value="/delpending",method=RequestMethod.POST)
	public ModelAndView delpending() {

    	System.err.println("\n\n\nINSIDE Controller delpending()\n\n\n");

    	try {
    		this.calculator.deleteAllPendingRequests();

    		List<CalculationRequest> pending = this.calculator.getAllPendingRequests();

    		if (null==pending || pending.isEmpty()) {
    			return new ModelAndView("/pending","message", "There are NO Pending Requests");
	 		}

    		return new ModelAndView("/pending","error","Oops! there are "+pending.size());

    	} catch (Exception e) {
    		return new ModelAndView("/results","error", "There was an error:"+e.getMessage());
    	}
	}

	@RequestMapping(value="/delresults",method=RequestMethod.POST)
	public ModelAndView delresults() {

    	System.err.println("\n\n\nINSIDE Controller delresults()\n\n\n");

    	try {
    		this.calculator.deleteAllResults();

    		List<CalculationResult> results = this.calculator.getAllCalculatedResults();

    		if (null==results || results.isEmpty()) {
			  	return new ModelAndView("/results","message", "There are NO Results");
    		}

    		return new ModelAndView("/results","error","Oops! there are "+results.size());

    	} catch (Exception e) {
    		return new ModelAndView("/results","error", "There was an error:"+e.getMessage());
    	}
	}

	@RequestMapping(value="/runpendingnostop",method=RequestMethod.POST)
	public ModelAndView runpendingnostop() {

    	System.err.println("\n\n\nINSIDE Controller runpending()\n\n\n");

    	try {

    		this.calculator.runAllPendingRequestsNoStopOnError();

    		List<CalculationResult> calculatedResults = this.calculator.getAllCalculatedResults();

    		if (null==calculatedResults || calculatedResults.isEmpty()) {
    			return new ModelAndView("/results","message", "There are NO Results");
    		}

			return new ModelAndView("/results","calculatedResults",calculatedResults);

    	} catch (Exception e) {
    		return new ModelAndView("/results","error", "There was an error:"+e.getMessage());
    	}
	}

}
