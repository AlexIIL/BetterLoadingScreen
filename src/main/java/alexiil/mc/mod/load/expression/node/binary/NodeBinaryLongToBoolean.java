package alexiil.mc.mod.load.expression.node.binary;

import alexiil.mc.mod.load.expression.NodeInliningHelper;
import alexiil.mc.mod.load.expression.api.Arguments;
import alexiil.mc.mod.load.expression.api.IExpressionNode.INodeBoolean;
import alexiil.mc.mod.load.expression.node.value.NodeImmutableBoolean;

public class NodeBinaryLongToBoolean implements INodeBoolean {
    public enum Type {
        EQUAL("==", (l, r) -> l == r),
        NOT_EQUAL("!=", (l, r) -> l != r),
        LESS_THAN("<", (l, r) -> l < r),
        LESS_THAN_OR_EQUAL("<=", (l, r) -> l <= r),
        GREATER_THAN(">", (l, r) -> l > r),
        GREATER_THAN_OR_EQUAL(">=", (l, r) -> l >= r);

        private final String op;
        private final BiLongToBooleanFunction operator;

        Type(String op, BiLongToBooleanFunction operator) {
            this.op = op;
            this.operator = operator;
        }

        public NodeBinaryLongToBoolean create(INodeLong left, INodeLong right) {
            return new NodeBinaryLongToBoolean(left, right, this);
        }
    }

    @FunctionalInterface
    public interface BiLongToBooleanFunction {
        boolean apply(long l, long r);
    }

    private final INodeLong left, right;
    private final Type type;

    private NodeBinaryLongToBoolean(INodeLong left, INodeLong right, Type type) {
        this.left = left;
        this.right = right;
        this.type = type;
    }

    @Override
    public boolean evaluate() {
        return type.operator.apply(left.evaluate(), right.evaluate());
    }

    @Override
    public INodeBoolean inline(Arguments args) {
        return NodeInliningHelper.tryInline(this, args, left, right, //
                (l, r) -> new NodeBinaryLongToBoolean(l, r, type), //
                (l, r) -> NodeImmutableBoolean.get(type.operator.apply(l.evaluate(), r.evaluate())));
    }

    @Override
    public String toString() {
        return "(" + left + ") " + type.op + " (" + right + ")";
    }

}
