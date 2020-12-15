package processor;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.RecordComponentElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * @author Filip Hrisafov
 */
@SupportedAnnotationTypes("org.mapstruct.Mapper")
public class TestProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            for (TypeElement annotation : annotations) {
                Set<? extends Element> mappers = roundEnv.getElementsAnnotatedWith(annotation);
                for (Element mapper : mappers) {
                    TypeElement mapperTypeElement = (TypeElement) mapper;

                    for (Element enclosedElement : mapperTypeElement.getEnclosedElements()) {
                        if (enclosedElement.getKind() == ElementKind.METHOD) {
                            validateMethod((ExecutableElement) enclosedElement, roundEnv);
                        }
                    }

                }

            }

        }

        return false;
    }

    protected void validateMethod(ExecutableElement method, RoundEnvironment roundEnv) {
        TypeMirror returnType = method.getReturnType();
        if (returnType.getKind() == TypeKind.DECLARED) {
            Element returnElement = ((DeclaredType) returnType).asElement();
            if (returnElement.getKind() == ElementKind.RECORD) {
                validateRecord((TypeElement) returnElement, roundEnv);
            }
        }

        for (VariableElement parameter : method.getParameters()) {
            TypeMirror parameterType = parameter.asType();
            if (parameterType.getKind() == TypeKind.DECLARED) {
                Element parameterElement = ((DeclaredType) parameterType).asElement();
                if (parameterElement.getKind() == ElementKind.RECORD) {
                    validateRecord((TypeElement) parameterElement, roundEnv);
                }
            }
        }

    }

    protected void validateRecord(TypeElement recordElement, RoundEnvironment roundEnv) {
        List<? extends RecordComponentElement> recordComponents = recordElement.getRecordComponents();

        if (recordComponents.isEmpty()) {
            processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Record element " + recordElement.getQualifiedName().toString() + " has no record components");
        } else {
            for (RecordComponentElement recordComponent : recordComponents) {
                ExecutableElement accessor = recordComponent.getAccessor();
                if (accessor == null) {
                    processingEnv.getMessager()
                            .printMessage(Diagnostic.Kind.ERROR,
                                    "Record component " + recordComponent.getSimpleName().toString() + " from record element " + recordElement
                                            .getQualifiedName().toString() + " has no accessor");
                }
            }
        }
    }
}
