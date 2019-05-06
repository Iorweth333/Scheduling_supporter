package ioiobagiety.service.impl;

@Service
public class ConflictsFinderServiceImpl implements ConflictsFinderService {

    @Transactional
    public List<Conflict> getAll () {
        List<Conflict> conflicts = Conflict.findAll();
        if (conflicts.size() > 0) {
            return conflicts;
        } else {
            throw new ResourceNotFoundException("Conflict");
        }
    }

}
