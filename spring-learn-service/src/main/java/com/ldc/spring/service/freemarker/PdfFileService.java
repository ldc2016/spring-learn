package com.ldc.spring.service.freemarker;


import com.alibaba.fastjson.JSON;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.ldc.spring.utils.ClassPathUtil;
import com.ldc.spring.utils.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

@Component
public class PdfFileService {

    public static final Logger LOG = LoggerFactory.getLogger(PdfFileService.class);
    /**
     * freemarker渲染html：将html模板文件中的占位字符替换成实际值
     */
    public String freeMarkerRender(Map<String, String> tmpParams, String contractTemplateFilePath, String htmlTmpRelativePath) throws RuntimeException {
        Writer out = new StringWriter();
        try {
            FileUtil.makeDir(contractTemplateFilePath);
            Configuration freemarkerCfg = new Configuration();
            freemarkerCfg.setDirectoryForTemplateLoading(new File(contractTemplateFilePath));
            // 获取模板,并设置编码方式
            Template template = freemarkerCfg.getTemplate(htmlTmpRelativePath);
            template.setEncoding("UTF-8");
            // 合并模板参数实际值与模板，并将合并后的数据和模板写入到字符流
            template.process(tmpParams, out);
            out.flush();
            return out.toString();
        } catch (Exception e) {
            LOG.warn("freeMarkerRender, 渲染html模板文件时发生异常, 入参tmpParams={},htmlTmpRelativePath={}", JSON.toJSON(tmpParams), JSON.toJSON(htmlTmpRelativePath));
            throw new RuntimeException("渲染html模板文件时发生异常",e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                LOG.error("[PdfFileUtil.freeMarkerRender]  渲染html模板时，释放I/O资源时发生异常：{}", ex.getMessage());
            }
        }

    }

    /**
     * @param content
     * @param pdfFileRelativePath
     * @param pdfFileName
     */
    public void createPdfFile(String content, String pdfFileRelativePath, String pdfFileName) throws RuntimeException {
        Document document = new Document();
        FileOutputStream fos = null;
        PdfWriter writer = null;
        try {
            File pdfFile = FileUtil.makeFileWhenNotExists(pdfFileRelativePath, pdfFileName);
            fos = new FileOutputStream(pdfFile);
            writer = PdfWriter.getInstance(document, fos);
            document.open();
            XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontImp.register(ClassPathUtil.getRelativePathForTest() + "config/STSONG.ttf");
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                    new ByteArrayInputStream(content.getBytes()), null, Charset.forName("UTF-8"), fontImp);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("创建PDF文件时发生FileNotFoundException异常", e);
        } catch (DocumentException e) {
            throw new RuntimeException("创建PDF文件时发生DocumentException异常", e);
        } catch (IOException e) {
            throw new RuntimeException("创建PDF文件时发生IOException异常", e);
        } catch (Exception e){
            throw new RuntimeException("创建PDF文件时发生异常", e);
        }finally {
            closeIoStreamResource(document, fos, writer);
        }
    }

    /**
     * @param content             文件内容
     * @param pdfFileRelativePath 文件保存的相对地址
     * @param pdfFileName         文件名称
     * @return
     */
    public boolean createSignedPdfFileFromStream(byte[] content, String pdfFileRelativePath, String pdfFileName) throws RuntimeException {
        Document document = null;
        PdfWriter writer = null;
        FileOutputStream fos = null;
        try {
            File singedPdfFile = FileUtil.makeFileWhenNotExists(pdfFileRelativePath, pdfFileName);
            FileUtils.writeByteArrayToFile(singedPdfFile, content);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("创建PDF文件时发生FileNotFoundException异常", e);
        } catch (IOException e) {
            throw new RuntimeException("创建PDF文件时发生IOException异常", e);
        } finally {
            closeIoStreamResource(document, fos, writer);
        }
        return true;
    }


    /**
     * 将指定路径的PDF文件转换成byte[]
     * @param pdfFileRelativePath
     * @param pdfFileName
     * @return
     */
    public byte[] convertPdfFileToBytes(String pdfFileRelativePath, String pdfFileName) throws RuntimeException {
        byte[] content = new byte[0];
        try {
            File file = new File(pdfFileRelativePath, pdfFileName);
            if (!file.exists()) {
                throw new RuntimeException("convertPdfFileToBytes时发现PDF文件不存在");
            }
            content = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            throw new RuntimeException("将PDF文件转换成字节流时发生异常", e);
        } catch (Exception e) {
            throw new RuntimeException("将PDF文件转换成字节流时发生异常", e);
        }
        return content;
    }

    private void closeIoStreamResource(Document document, FileOutputStream fos, PdfWriter writer) {
        try {
            if (document != null) {
                document.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (fos != null) {
                fos.close();
            }
        } catch (IOException e) {
            LOG.error("[PdfFileUtil.createPdfFile]  创建PDF文件时，释放I/O资源时发生异常：{}", e.getMessage());
        }
    }
}
