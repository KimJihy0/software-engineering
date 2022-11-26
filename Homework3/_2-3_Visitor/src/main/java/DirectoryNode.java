import java.io.File;

public class DirectoryNode implements FileSystemNode {
    public DirectoryNode(File directory) {
        this.directory = directory;
    }

    public void accept(FileSystemVisitor v) {
        v.visitDirectoryNode(this);
    }

    public File getDirectory() {
        return directory;
    }

    public FileSystemNode[] getChildren() {
        File[] files = directory.listFiles();
        assert files != null;
        FileSystemNode[] children = new FileSystemNode[files.length];
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory())
                children[i] = new DirectoryNode(f);
            else
                children[i] = new FileNode(f);
        }
        return children;
    }

    private final File directory;
}
