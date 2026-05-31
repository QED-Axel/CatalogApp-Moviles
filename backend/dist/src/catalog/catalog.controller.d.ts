import { CatalogService } from './catalog.service';
import { CreateMediaItemDto } from './dto/create-media-item.dto';
import { UpdateMediaItemDto } from './dto/update-media-item.dto';
export declare class CatalogController {
    private readonly catalogService;
    constructor(catalogService: CatalogService);
    create(createMediaItemDto: CreateMediaItemDto): Promise<any>;
    findAll(): Promise<any>;
    findOne(id: number): Promise<any>;
    update(id: number, updateMediaItemDto: UpdateMediaItemDto): Promise<any>;
    remove(id: number): Promise<any>;
    syncTmdb(): Promise<{
        message: string;
        result: {
            count: number;
        };
    }>;
}
