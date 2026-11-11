package pl.pollub.hotel_management_project.common;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " with id " + id + " not found");
    }
}
