/**
 * Copyright (C) 2010-2016 eBusiness Information, Excilys Group
 * Copyright (C) 2016-2019 the AndroidAnnotations project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed To in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.androidannotations.internal.core.handler;

import java.util.List;

import javax.lang.model.element.Element;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.helper.IdValidatorHelper;
import org.androidannotations.holder.HasOptionsMenu;
import org.androidannotations.rclass.IRClass;

import com.helger.jcodemodel.JBlock;
import com.helger.jcodemodel.JFieldRef;
import com.helger.jcodemodel.JVar;

public class OptionsMenuHandler extends BaseAnnotationHandler<HasOptionsMenu> {

	public OptionsMenuHandler(AndroidAnnotationsEnvironment environment) {
		super(OptionsMenu.class, environment);
	}

	@Override
	public void validate(Element element, ElementValidation validation) {
		validatorHelper.hasEActivityOrEFragment(element, validation);

		validatorHelper.resIdsExist(element, IRClass.Res.MENU, IdValidatorHelper.FallbackStrategy.NEED_RES_ID, validation);
	}

	@Override
	public void process(Element element, HasOptionsMenu holder) {
		JBlock body = holder.getOnCreateOptionsMenuMethodInflateBody();
		JVar menuInflater = holder.getOnCreateOptionsMenuMenuInflaterVar();
		JVar menuParam = holder.getOnCreateOptionsMenuMenuParam();

		List<JFieldRef> fieldRefs = annotationHelper.extractAnnotationFieldRefs(element, IRClass.Res.MENU, false);
		for (JFieldRef optionsMenuRefId : fieldRefs) {
			body.add(menuInflater.invoke("inflate").arg(optionsMenuRefId).arg(menuParam));
		}
	}
}
