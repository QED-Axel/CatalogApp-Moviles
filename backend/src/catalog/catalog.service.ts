import { Injectable, NotFoundException } from '@nestjs/common';
import { PrismaService } from '../prisma/prisma.service';
import { CreateMediaItemDto } from './dto/create-media-item.dto';
import { UpdateMediaItemDto } from './dto/update-media-item.dto';

@Injectable()
export class CatalogService {
  constructor(private prisma: PrismaService) {}

  async create(createMediaItemDto: CreateMediaItemDto) {
    return this.prisma.mediaItem.create({
      data: createMediaItemDto,
    });
  }

  async findAll() {
    return this.prisma.mediaItem.findMany({
      orderBy: { createdAt: 'desc' },
    });
  }

  async findOne(id: number) {
    const item = await this.prisma.mediaItem.findUnique({
      where: { id },
    });
    if (!item) {
      throw new NotFoundException(`MediaItem with ID ${id} not found`);
    }
    return item;
  }

  async update(id: number, updateMediaItemDto: UpdateMediaItemDto) {
    try {
      return await this.prisma.mediaItem.update({
        where: { id },
        data: updateMediaItemDto,
      });
    } catch (error) {
      throw new NotFoundException(`MediaItem with ID ${id} not found`);
    }
  }

  async remove(id: number) {
    try {
      return await this.prisma.mediaItem.delete({
        where: { id },
      });
    } catch (error) {
      throw new NotFoundException(`MediaItem with ID ${id} not found`);
    }
  }
}
