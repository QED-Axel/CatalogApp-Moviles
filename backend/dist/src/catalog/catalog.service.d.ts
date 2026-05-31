import { PrismaService } from '../prisma/prisma.service';
import { CreateMediaItemDto } from './dto/create-media-item.dto';
import { UpdateMediaItemDto } from './dto/update-media-item.dto';
export declare class CatalogService {
    private prisma;
    constructor(prisma: PrismaService);
    create(createMediaItemDto: CreateMediaItemDto): Promise<any>;
    findAll(): Promise<any>;
    findOne(id: number): Promise<any>;
    update(id: number, updateMediaItemDto: UpdateMediaItemDto): Promise<any>;
    remove(id: number): Promise<any>;
    syncTmdb(): Promise<{
        count: number;
    }>;
}
