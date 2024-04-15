package me.tpgc.type_finder;

import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.codeInsight.template.macro.MacroBase;
import com.intellij.psi.PsiDocumentManager;
import com.jetbrains.cidr.lang.daemon.clang.clangd.ClangdBridge;
import com.jetbrains.cidr.lang.daemon.clang.clangd.lsp.ClangdLanguageService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TypeMacro extends MacroBase {

    public TypeMacro() {
        super("findType", "findType(String)");
    }

    @Override
    protected @Nullable Result calculateResult(Expression @NotNull [] params, ExpressionContext context, boolean quick) {
        String variable = getTextResult(params, context);
        var project = context.getProject();
        var editor = context.getEditor();
        if (variable != null && !variable.isEmpty() && project != null && editor != null) {
            var text = editor.getDocument().getText();
            var lines = text.split("\n");
            String type = null;
            for (var v : lines) {
                if (v.contains(variable) && v.matches(".* .*[=;]")) {
                    var spaced = v.split(" ");
                    int index = 0;
                    for (int i = 0; i < spaced.length; i++) {
                        if (spaced[i].equals(variable)) {
                            index = i;
                            break;
                        }
                    }
                    if (index > 0) {
                        type = spaced[index - 1];
                        break;
                    }
                }
            }
            if (type != null)
                return new TextResult(type);
        }
        return null;
    }


}
