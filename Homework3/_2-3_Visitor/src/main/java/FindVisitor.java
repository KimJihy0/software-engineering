import java.util.Stack;

public class FindVisitor implements FileSystemVisitor {
    public FindVisitor(String target) {
        this.target = target;
    }

    public void visitFileNode(FileNode node) {
        String fileName = node.getFile().getName();
        if (fileName.matches(target)) {
            System.out.print("- ");
            printPath();
            System.out.println(fileName);
        }
    }

    public void visitDirectoryNode(DirectoryNode node) {
        String directoryName = node.getDirectory().getName();
        if (directoryName.matches(target)) {
            System.out.print("d ");
            printPath();
            System.out.println(directoryName);
        }
        pathStack.push(node);
        for (FileSystemNode c : node.getChildren())
            c.accept(this);
        pathStack.pop();
    }

    private void printPath() {
        for (DirectoryNode node : pathStack) {
            System.out.print(node.getDirectory().getName());
            System.out.print('/');
        }
    }

    private final String target;

    private final Stack<DirectoryNode> pathStack = new Stack<>();
}
