package BuilderPatternExample;

public class Computer {
    // Required and optional attributes
    private String cpu;
    private String ram;
    private String storage;
    private String gpu;
    private String os;

    // Private constructor – only Builder can access it
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
        this.os = builder.os;
    }

    // Static nested Builder class
    public static class Builder {
        private String cpu;
        private String ram;
        private String storage;
        private String gpu;
        private String os;

        public Builder setCpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder setRam(String ram) {
            this.ram = ram;
            return this;
        }

        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder setGpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public Builder setOs(String os) {
            this.os = os;
            return this;
        }

        // Final step: build the Computer object
        public Computer build() {
            return new Computer(this);
        }
    }

    @Override
    public String toString() {
        return "Computer [CPU=" + cpu + ", RAM=" + ram + ", Storage=" + storage +
               ", GPU=" + gpu + ", OS=" + os + "]";
    }
}

public class Main {
    public static void main(String[] args) {
        // High-end gaming PC configuration
        Computer gamingPC = new Computer.Builder()
                .setCpu("Intel Core i9")
                .setRam("32GB")
                .setStorage("1TB SSD")
                .setGpu("NVIDIA RTX 4090")
                .setOs("Windows 11")
                .build();

        // Basic office PC configuration
        Computer officePC = new Computer.Builder()
                .setCpu("Intel Core i5")
                .setRam("8GB")
                .setStorage("500GB HDD")
                .setOs("Windows 10")
                .build();

        // Display configurations
        System.out.println("Gaming PC: " + gamingPC);
        System.out.println("Office PC: " + officePC);
    }
}
