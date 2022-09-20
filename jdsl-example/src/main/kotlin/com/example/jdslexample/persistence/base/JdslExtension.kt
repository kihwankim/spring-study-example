package com.example.jdslexample.persistence.base

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.*

data class NestedColumnSpec<T>(
    val nestedColumnSpec: ColumnSpec<*>,
    val path: String
) : ExpressionSpec<T> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return nestedColumnSpec.nestedPath(froms).get(path)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return nestedColumnSpec.nestedPath(froms).get(path)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return nestedColumnSpec.nestedPath(froms).get(path)
    }

    private fun <T> ColumnSpec<T>.nestedPath(froms: Froms): Path<T> =
        froms[entity].get(path)
}
