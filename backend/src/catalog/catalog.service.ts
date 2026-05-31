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

  async syncTmdb() {
    const apiKey = process.env.TMDB_API_KEY;
    if (!apiKey) throw new NotFoundException('Falta TMDB_API_KEY en el entorno');

    const response = await fetch(`https://api.themoviedb.org/3/movie/popular?api_key=${apiKey}&language=es-MX&page=1`);
    const data = await response.json();

    if (!data.results) return { count: 0 };

    let count = 0;
    for (const item of data.results) {
      const yearStr = item.release_date ? item.release_date.split('-')[0] : '2000';
      const yearNum = parseInt(yearStr, 10);

      await this.prisma.mediaItem.create({
        data: {
          title: item.title,
          type: 'movie',
          synopsis: item.overview || 'Sin descripción',
          imageUrl: item.poster_path ? `https://image.tmdb.org/t/p/w500${item.poster_path}` : null,
          year: isNaN(yearNum) ? 2000 : yearNum,
          genre: 'Popular' // Simplificación para la fase inicial
        }
      });
      count++;
    }
    return { count };
  }
}
