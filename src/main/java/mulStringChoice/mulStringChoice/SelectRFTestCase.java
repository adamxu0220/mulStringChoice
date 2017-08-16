package mulStringChoice.mulStringChoice;
import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.bind.JavaScriptMethod;


import hudson.Extension;
import hudson.Util;
import hudson.model.Descriptor;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
 * @author hzxu
 *
 */
public class SelectRFTestCase extends ParameterDefinition {

	private static final long serialVersionUID = 9032072543915872650L;
	private static final Logger LOGGER = Logger.getLogger(SelectRFTestCase.class.getName());
	static String sDisplayName = "mulString Choice";
	static String sNameCanNotBeEmpty = "Name can not be empty";
	
	@Extension
	public static class DescriptorImpl extends ParameterDescriptor {
		@Override
		public String getDisplayName() {
			return sDisplayName;
		}
		
		public FormValidation doCheckName(@QueryParameter final String name) throws IOException {
			if(StringUtils.isBlank(name)) {
				return FormValidation.error(sNameCanNotBeEmpty);
			}
			return FormValidation.ok();
		}
		
		
		
		public FormValidation doCheckVarsString(@QueryParameter final String VarsString) {
			return FormValidation.ok();	
		}
			
		

		public DescriptorImpl() {
			LOGGER.warning("in DescriptorImpl");
			load();
		}
		
		public boolean configure(org.kohsuke.stapler.StaplerRequest req,
					net.sf.json.JSONObject json)
		throws Descriptor.FormException{
			LOGGER.warning("in configure");
			return false;
		}
		
		@Override
		public SelectRFTestCase newInstance(StaplerRequest req,
							JSONObject formData)
                throws hudson.model.Descriptor.FormException
		{
			LOGGER.warning("in newInstance");
			LOGGER.warning(formData.toString());
			
			return new SelectRFTestCase(
							formData.getString("name"),
							null,//formData.getString("description"),
							formData.getString("VarsString"),
							formData.getString("SelectedVarsStr")
							);
        	}
		
	}
	

		

	private String VarsString;
	private List<String> VarsList=null;
	private String SelectedVarsStr;	
	
	
	/**
	 * @param name
	 * @param description
         * @param VarsString
	 * @param VarsJA
	 */
	@DataBoundConstructor
	public SelectRFTestCase(String name, String description, String VarsString, String SelectedVarsStr) {

		super(name, description);
		LOGGER.warning("@@@@@@@@@in SelectRFTestCase");
		this.VarsString = VarsString;
		this.SelectedVarsStr = SelectedVarsStr;
		LOGGER.warning("VarsString " + VarsString + " SelectedVarsStr " + SelectedVarsStr);
	}

	
	
	@Override
	public ParameterValue createValue(StaplerRequest request) {
		LOGGER.warning("in createValue(StaplerRequest request)");
		String value[] = request.getParameterValues(getName());
		if(value == null) {
			return getDefaultParameterValue();
		}
		return null;
	}

	@Override
	public ParameterValue createValue(StaplerRequest request, JSONObject jO) {

		LOGGER.warning("in createValue(StaplerRequest request, JSONObject jO)");
		LOGGER.warning("jO:"+jO.toString()); 
		return new FileSystemListParameterValue(jO.get("name").toString(), jO.get("SelectedVarsStr").toString());
	}


	


	

	public String getVarsString() {
		return VarsString;
	}


	public List<String> getVarsList() {
		LOGGER.warning("in getVarsList:'"+VarsString+"'");
		List<String> tmp = new ArrayList<String>();
		for (String s : VarsString.split(";")){
			tmp.add(s);
		}
		return tmp;
	}

	public String getSelectedVarsStr() {
		/*
		String tmp = null;
		for (int idx=0; idx < SelectedVarsJA.size(); idx ++){
			if (tmp == null ){
				tmp = SelectedVarsJA.getString(idx).toString();
			}
			else{		
				tmp += ";" + SelectedVarsJA.getString(idx).toString();
			}
		}
		LOGGER.warning("in getSelectedVarsList:'"+"'");
		return tmp;
		*/
		return this.SelectedVarsStr;
	}
	public List<String> getSelectedVarsList() {
		/*
                List<String> tmp = new ArrayList<String>();
                for (int idx=0; idx < SelectedVarsJA.size(); idx ++){
                        tmp.add(SelectedVarsJA.getString(idx).toString());
                }
                LOGGER.warning("in getSelectedVarsList:'"+tmp.toString()+"'");
                return tmp;
		*/
		//SelectedVarsList;
		List<String> tmp = new ArrayList<String>();
		for (String s : this.SelectedVarsStr.split(";")){
                        tmp.add(s);
                }
		return tmp;
        }

	@JavaScriptMethod
	public void setSelectedVars(String SelectedVarsStr){
		/*
		LOGGER.warning("in SetSelectedVars:"+SelectedVarsStr+"");
		SelectedVarsJA.clear();
		for (String s : SelectedVarsStr.split(";")){
			SelectedVarsJA.add(s);
		}*/
		this.SelectedVarsStr = SelectedVarsStr;
	}
}
